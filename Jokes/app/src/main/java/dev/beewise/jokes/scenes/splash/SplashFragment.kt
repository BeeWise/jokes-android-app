package dev.beewise.jokes.scenes.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import dev.beewise.jokes.R
import dev.beewise.jokes.extensions.fragment.runOnUiThread
import java.lang.ref.WeakReference

interface SplashDisplayLogic {
    fun displaySetupScene(viewModel: SplashModels.SceneSetup.ViewModel)
    fun displayDismissScene()
}

interface SplashFragmentDelegate {
    fun splashFragmentDismissScene(fragment: SplashFragment)
    fun splashFragmentNavigateToJokes(fragment: SplashFragment)
}

class SplashFragment: Fragment(), SplashDisplayLogic {
    companion object {
        const val TAG: String = "SplashFragmentTag"

        fun fragment(): SplashFragment {
            return SplashFragment()
        }
    }

    var interactor: SplashBusinessLogic? = null

    lateinit var contentView: ConstraintLayout
    private lateinit var imageView: ImageView

    var delegate: WeakReference<SplashFragmentDelegate>? = null

    init {
        val presenter = SplashPresenter(this)
        val interactor = SplashInteractor()
        interactor.presenter = presenter
        this.interactor = interactor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.splash_fragment, container, false)
        this.findSubviews(rootView)
        this.setupSubviews()
        this.setupSubviewsConstraints()
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.interactor?.shouldFetchUserDetails()
    }

    // region Subviews
    private fun findSubviews(view: View) {
        this.contentView = view.findViewById(R.id.contentViewId)
    }

    private fun setupSubviews() {
        this.setupImageView()
    }

    private fun setupImageView() {
        val imageView = ImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = ConstraintLayout.LayoutParams(0, 0)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageDrawable(SplashStyle.instance.contentViewModel.backgroundImage())
        this.contentView.addView(imageView)
        this.imageView = imageView
    }
    //endregion

    //region Constraints
    private fun setupSubviewsConstraints() {
        this.setupImageViewConstraints()
    }

    private fun setupImageViewConstraints() {
        this.imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }
    // endregion

    //region Display logic
    override fun displaySetupScene(viewModel: SplashModels.SceneSetup.ViewModel) {
        this.runOnUiThread {
            if (viewModel.type == SplashModels.SceneType.jokes) {
                this.delegate?.get()?.splashFragmentNavigateToJokes(this)
            }
        }
    }

    override fun displayDismissScene() {
        this.runOnUiThread {
            this.delegate?.get()?.splashFragmentDismissScene(this)
        }
    }
    //endregion
}
