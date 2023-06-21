package dev.beewise.jokes.scenes.jokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R
import dev.beewise.jokes.components.views.LoadingImageView
import dev.beewise.jokes.components.views.LogoNavigationView
import dev.beewise.jokes.components.views.LogoNavigationViewDelegate
import dev.beewise.jokes.components.views.UserAvatarView
import dev.beewise.jokes.extensions.fragment.runOnUiThread
import dev.beewise.jokes.extensions.recycler_view.shouldLoadMore
import dev.beewise.jokes.models.image.CompoundImage
import dev.beewise.jokes.style.ApplicationConstraints
import dev.beewise.jokes.style.ApplicationStyle
import java.lang.ref.WeakReference

interface JokesDisplayLogic {
    fun displayLoadingState()
    fun displayNotLoadingState()

    fun displayItems(viewModel: JokesModels.ItemsPresentation.ViewModel)

    fun displayNoMoreItems(viewModel: JokesModels.NoMoreItemsPresentation.ViewModel)
    fun displayRemoveNoMoreItems()

    fun displayError(viewModel: JokesModels.ErrorPresentation.ViewModel)
    fun displayRemoveError()

    fun displayReadState(viewModel: JokesModels.ItemReadState.ViewModel)

    fun displayScrollToItem(viewModel: JokesModels.ItemScroll.ViewModel)

    fun displayUserAvatarImage(viewModel: JokesModels.UserAvatarImage.ViewModel)
    fun displayUserAvatarImageLoadingState(viewModel: JokesModels.UserAvatarImageLoadingState.ViewModel)
}

public interface JokesFragmentDelegate {
    fun jokesFragmentDismissScene(fragment: JokesFragment)
}

class JokesFragment: Fragment(), JokesDisplayLogic, JokesAdapterDelegate, LogoNavigationViewDelegate {
    companion object {
        const val TAG: String = "JokesFragmentTag"

        fun fragment(): JokesFragment {
            return JokesFragment()
        }
    }

    var interactor: JokesBusinessLogic? = null

    lateinit var contentView: ConstraintLayout
    lateinit var backgroundImageView: ImageView
    lateinit var navigationBarView: LogoNavigationView
    lateinit var adapter: JokesAdapter
    lateinit var recyclerView: RecyclerView

    var delegate: WeakReference<JokesFragmentDelegate>? = null

    init {
        val presenter = JokesPresenter(this)
        val interactor = JokesInteractor()
        interactor.presenter = presenter
        this.interactor = interactor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.jokes_fragment, container, false)
        this.findSubviews(rootView)
        this.setupSubviews()
        this.setupSubviewsConstraints()
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.interactor?.shouldFetchJokes()
    }

    // region Subviews
    private fun findSubviews(view: View) {
        this.contentView = view.findViewById(R.id.contentViewId)
    }

    private fun setupSubviews() {
        this.setupBackgroundImageView()
        this.setupNavigationBarView()
        this.setupAdapter()
        this.setupRecyclerView()
    }

    private fun setupBackgroundImageView() {
        val imageView = ImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = ConstraintLayout.LayoutParams(0, 0)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageDrawable(ApplicationStyle.instance.wallBackgroundImage(ApplicationStyle.ImageType.drawable))
        this.contentView.addView(imageView)
        this.backgroundImageView = imageView
    }

    private fun setupNavigationBarView() {
        val context = this.context ?: return

        val loadingImageModel = LoadingImageView.Model(CompoundImage(), false)
        val userAvatarModel = UserAvatarView.Model(loadingImageModel)
        val model = LogoNavigationView.Model(userAvatarModel)
        model.includeBack = false
        model.includeUserAvatar = false
        model.includeSeparator = true

        val navigationBarView = LogoNavigationView(context)
        navigationBarView.id = View.generateViewId()
        navigationBarView.layoutParams = ConstraintLayout.LayoutParams(0, 0)
        navigationBarView.delegate = WeakReference(this)
        navigationBarView.setModel(model)
        this.contentView.addView(navigationBarView)
        this.navigationBarView = navigationBarView
    }

    override fun logoNavigationViewOnPressBackButton(view: LogoNavigationView) {

    }

    override fun logoNavigationViewOnPressLogoImage(view: LogoNavigationView) {
        this.interactor?.shouldSelectLogo()
    }

    override fun logoNavigationViewOnPressUserAvatar(view: LogoNavigationView) {

    }

    private fun setupAdapter() {
        this.adapter = JokesAdapter(this)
    }

    override fun jokesAdapterJokeQuestionAnswerCellOnPressReadAnswer(adapter: JokesAdapter, id: String?) {
        this.interactor?.shouldSelectReadAnswer(JokesModels.ItemSelection.Request(id))
    }

    override fun jokesAdapterJokeQuestionAnswerCellShouldFetchUserAvatarImage(adapter: JokesAdapter, id: String?, hasImage: Boolean, isLoadingImage: Boolean) {
        this.interactor?.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request(id, hasImage, isLoadingImage))
    }

    override fun jokesAdapterJokeTextCellShouldFetchUserAvatarImage(adapter: JokesAdapter, id: String?, hasImage: Boolean, isLoadingImage: Boolean) {
        this.interactor?.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request(id, hasImage, isLoadingImage))
    }

    private fun setupRecyclerView() {
        val context = this.context ?: return
        val recyclerView = RecyclerView(context)
        recyclerView.id = View.generateViewId()
        recyclerView.layoutParams = ConstraintLayout.LayoutParams(0, 0)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = this.adapter
        recyclerView.setBackgroundColor(JokesStyle.instance.recyclerViewModel.backgroundColor())
        recyclerView.shouldLoadMore {
            this.interactor?.shouldFetchJokes()
        }
        this.contentView.addView(recyclerView)
        this.recyclerView = recyclerView
    }
    //endregion

    //region Constraints
    private fun setupSubviewsConstraints() {
        this.setupBackgroundImageViewConstraints()
        this.setupNavigationBarViewConstraints()
        this.setupRecyclerViewConstraints()
    }

    private fun setupBackgroundImageViewConstraints() {
        this.backgroundImageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }

    private fun setupNavigationBarViewConstraints() {
        this.navigationBarView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToTop = this@JokesFragment.recyclerView.id
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

            this.height = ApplicationConstraints.navigationBarHeight()
        }
    }

    private fun setupRecyclerViewConstraints() {
        this.recyclerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToBottom = this@JokesFragment.navigationBarView.id
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }
    // endregion

    //region Display logic
    override fun displayLoadingState() {
        this.runOnUiThread { 
            this.adapter.setNewIsLoading(true)
        }
    }

    override fun displayNotLoadingState() {
        this.runOnUiThread { 
            this.adapter.setNewIsLoading(false)
        }
    }

    override fun displayItems(viewModel: JokesModels.ItemsPresentation.ViewModel) {
        this.runOnUiThread { 
            this.adapter.setNewItems(viewModel.items)
        }
    }

    override fun displayNoMoreItems(viewModel: JokesModels.NoMoreItemsPresentation.ViewModel) {
        this.runOnUiThread {
            this.adapter.noMoreItemsText = viewModel.text
            this.adapter.setNewNoMoreItems(true)
        }
    }

    override fun displayRemoveNoMoreItems() {
        this.runOnUiThread { 
            this.adapter.noMoreItemsText = null
            this.adapter.setNewNoMoreItems(false)
        }
    }

    override fun displayError(viewModel: JokesModels.ErrorPresentation.ViewModel) {
        this.runOnUiThread {
            this.adapter.errorText = viewModel.text
            this.adapter.setNewHasError(true)
        }
    }

    override fun displayRemoveError() {
        this.runOnUiThread { 
            this.adapter.errorText = null
            this.adapter.setNewHasError(false)
        }
    }

    override fun displayReadState(viewModel: JokesModels.ItemReadState.ViewModel) {
        this.runOnUiThread { 
            this.adapter.getIndexedJokeQnaModel(viewModel.id)?.let {
                it.second.isRead = viewModel.isRead
                this.adapter.notifyItemChanged(it.first)
            }
        }
    }

    override fun displayScrollToItem(viewModel: JokesModels.ItemScroll.ViewModel) {
        this.runOnUiThread { 
            this.recyclerView.scrollToPosition(viewModel.index)
        }
    }

    override fun displayUserAvatarImage(viewModel: JokesModels.UserAvatarImage.ViewModel) {
        this.runOnUiThread {
            this.adapter.getIndexedJokeTextModel(viewModel.uuid)?.let {
                it.second.avatar.loadingImage.image.bitmap = viewModel.image
                it.second.avatar.loadingImage.viewInterface?.get()?.setImage(it.second.avatar.loadingImage.image)
            } ?: this.adapter.getIndexedJokeQnaModel(viewModel.uuid)?.let {
                it.second.avatar.loadingImage.image.bitmap = viewModel.image
                it.second.avatar.loadingImage.viewInterface?.get()?.setImage(it.second.avatar.loadingImage.image)
            }
        }
    }

    override fun displayUserAvatarImageLoadingState(viewModel: JokesModels.UserAvatarImageLoadingState.ViewModel) {
        this.runOnUiThread {
            this.adapter.getIndexedJokeTextModel(viewModel.uuid)?.let {
                it.second.avatar.loadingImage.isLoading = viewModel.isLoadingImage
                it.second.avatar.loadingImage.viewInterface?.get()?.setIsLoadingImage(viewModel.isLoadingImage)
            } ?: this.adapter.getIndexedJokeQnaModel(viewModel.uuid)?.let {
                it.second.avatar.loadingImage.isLoading = viewModel.isLoadingImage
                it.second.avatar.loadingImage.viewInterface?.get()?.setIsLoadingImage(viewModel.isLoadingImage)
            }
        }
    }
    //endregion
}
