package dev.beewise.jokes.scenes.jokes

import android.graphics.drawable.Drawable
import android.text.format.DateUtils
import android.widget.ImageView
import dev.beewise.jokes.application.JokesApplicationLocalization
import dev.beewise.jokes.components.buttons.ImageTitleButton
import dev.beewise.jokes.components.cells.JokeQuestionAnswerCell
import dev.beewise.jokes.components.cells.JokeTextCell
import dev.beewise.jokes.components.cells.SpaceCell
import dev.beewise.jokes.components.views.LoadingImageView
import dev.beewise.jokes.components.views.UserAvatarView
import dev.beewise.jokes.models.image.CompoundImage
import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.models.joke.JokeType
import dev.beewise.jokes.models.user.User
import dev.beewise.jokes.style.ApplicationConstraints
import toSpannableString
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

interface JokesPresentationLogic {
    fun presentLoadingState()
    fun presentNotLoadingState()

    fun presentItems(response: JokesModels.ItemsPresentation.Response)

    fun presentNoMoreItems()
    fun presentRemoveNoMoreItems()

    fun presentError(response: JokesModels.ErrorPresentation.Response)
    fun presentRemoveError()

    fun presentReadState(response: JokesModels.ItemReadState.Response)

    fun presentScrollToItem(response: JokesModels.ItemScroll.Response)
}

class JokesPresenter(displayLogic: JokesDisplayLogic) : JokesPresentationLogic {
    var displayer: WeakReference<JokesDisplayLogic>? = null

    var timeDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.getDefault())

    init {
        this.displayer = WeakReference(displayLogic)
    }

    override fun presentLoadingState() {
        this.displayer?.get()?.displayLoadingState()
    }

    override fun presentNotLoadingState() {
        this.displayer?.get()?.displayNotLoadingState()
    }

    override fun presentItems(response: JokesModels.ItemsPresentation.Response) {
        this.displayer?.get()?.displayItems(JokesModels.ItemsPresentation.ViewModel(this.displayedItems(response.items, response.readJokes)))
    }

    override fun presentNoMoreItems() {
        val text = JokesLocalization.instance.noMoreItemsText().toSpannableString(JokesStyle.instance.recyclerViewModel.noMoreItemsSpans())
        this.displayer?.get()?.displayNoMoreItems(JokesModels.NoMoreItemsPresentation.ViewModel(text))
    }

    override fun presentRemoveNoMoreItems() {
        this.displayer?.get()?.displayRemoveNoMoreItems()
    }

    override fun presentError(response: JokesModels.ErrorPresentation.Response) {
        val text = JokesLocalization.instance.errorText().toSpannableString(JokesStyle.instance.recyclerViewModel.errorSpans())
        this.displayer?.get()?.displayError(JokesModels.ErrorPresentation.ViewModel(text))
    }

    override fun presentRemoveError() {
        this.displayer?.get()?.displayRemoveError()
    }

    override fun presentReadState(response: JokesModels.ItemReadState.Response) {
        this.displayer?.get()?.displayReadState(JokesModels.ItemReadState.ViewModel(response.isRead, response.id))
    }

    override fun presentScrollToItem(response: JokesModels.ItemScroll.Response) {
        this.displayer?.get()?.displayScrollToItem(JokesModels.ItemScroll.ViewModel(response.animated, response.index))
    }

    //region Items
    private fun displayedItems(items: ArrayList<Joke>, readJokes: ArrayList<Joke>): ArrayList<JokesModels.DisplayedItem> {
        if (items.size == 0) return arrayListOf()
        val displayedItems: ArrayList<JokesModels.DisplayedItem> = arrayListOf()
        displayedItems.add(this.displayedSpaceItem(ApplicationConstraints.constant.x16.value))
        items.forEachIndexed { index, item ->
            val isRead = readJokes.any { it.uuid == item.uuid }
            displayedItems.add(this.displayedJokeItem(item, isRead))

            if (index != items.size - 1) {
                displayedItems.add(this.displayedSpaceItem(ApplicationConstraints.constant.x16.value))
            }
        }
        return displayedItems
    }

    private fun displayedSpaceItem(height: Int): JokesModels.DisplayedItem {
        return JokesModels.DisplayedItem(type = JokesModels.ItemType.space, model = SpaceCell.Model(height))
    }
    //endregion

    private fun displayedJokeItem(joke: Joke, isRead: Boolean): JokesModels.DisplayedItem {
        if (joke.type == JokeType.qna.value && joke.answer != null) {
            return this.displayedJokeQnaItem(joke, isRead)
        }
        return this.displayedJokeTextItem(joke)
    }

    private fun displayedJokeTextItem(joke: Joke): JokesModels.DisplayedItem {
        val avatar = this.jokeAvatarViewModel(joke)
        val likeCount = this.jokeLikeViewModel(joke)
        val dislikeCount = this.jokeDislikeViewModel(joke)

        val model = JokeTextCell.Model(avatar)
        model.id = joke.uuid
        model.name = joke.user?.name?.toSpannableString(JokesStyle.instance.jokeCellModel.nameSpans())
        model.username = this.usernameText(joke.user)?.toSpannableString(JokesStyle.instance.jokeCellModel.usernameSpans())
        model.text = joke.text?.toSpannableString(JokesStyle.instance.jokeCellModel.textSpans())
        model.likeCount = likeCount
        model.dislikeCount = dislikeCount
        model.time = this.time(joke.createdAt)?.toSpannableString(JokesStyle.instance.jokeCellModel.timeSpans())
        return JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeText, model = model)
    }

    private fun displayedJokeQnaItem(joke: Joke, isRead: Boolean): JokesModels.DisplayedItem {
        val avatar = this.jokeAvatarViewModel(joke)
        val likeCount = this.jokeLikeViewModel(joke)
        val dislikeCount = this.jokeDislikeViewModel(joke)

        val model = JokeQuestionAnswerCell.Model(avatar)
        model.id = joke.uuid
        model.name = joke.user?.name?.toSpannableString(JokesStyle.instance.jokeCellModel.nameSpans())
        model.username = this.usernameText(joke.user)?.toSpannableString(JokesStyle.instance.jokeCellModel.usernameSpans())
        model.text = joke.text?.toSpannableString(JokesStyle.instance.jokeCellModel.textSpans())
        model.answer = joke.answer?.toSpannableString(JokesStyle.instance.jokeCellModel.answerSpans())
        model.likeCount = likeCount
        model.dislikeCount = dislikeCount
        model.time = this.time(joke.createdAt)?.toSpannableString(JokesStyle.instance.jokeCellModel.timeSpans())
        model.isRead = isRead
        return JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeQna, model = model)
    }

    private fun jokeAvatarViewModel(joke: Joke): UserAvatarView.Model {
        val loadingImage = LoadingImageView.Model(this.compoundImage(joke.user?.photo?.url150, JokesStyle.instance.jokeCellModel.avatarPlaceholder()), false)
        loadingImage.activityIndicatorColor = JokesStyle.instance.jokeCellModel.avatarActivityColor()
        loadingImage.borderRadius = JokesStyle.instance.jokeCellModel.avatarBorderRadius

        val avatar = UserAvatarView.Model(loadingImage)
        avatar.isDisabled = true
        avatar.backgroundColor = JokesStyle.instance.jokeCellModel.avatarBackgroundColor()
        avatar.borderWidth = JokesStyle.instance.jokeCellModel.avatarBorderWidth
        avatar.borderColor = JokesStyle.instance.jokeCellModel.avatarBorderColor()
        avatar.margin = JokesStyle.instance.jokeCellModel.avatarMargin
        return avatar
    }

    private fun jokeLikeViewModel(joke: Joke): ImageTitleButton.Model {
        val likeCount = ImageTitleButton.Model()
        likeCount.activityIndicatorColor = JokesStyle.instance.jokeCellModel.likeCountActivityColor()
        likeCount.image = CompoundImage(null, null, JokesStyle.instance.jokeCellModel.likeCountImage())
        likeCount.imageTintColor = JokesStyle.instance.jokeCellModel.unselectedLikeCountTintColor()
        likeCount.backgroundColor = JokesStyle.instance.jokeCellModel.unselectedLikeCountBackgroundColor()
        likeCount.title = joke.likeCount.toString().toSpannableString(JokesStyle.instance.jokeCellModel.unselectedLikeCountSpans())
        likeCount.borderRadius = ApplicationConstraints.constant.x12.value
        likeCount.isDisabled = true
        likeCount.isLoading = false
        return likeCount
    }

    private fun jokeDislikeViewModel(joke: Joke): ImageTitleButton.Model {
        val dislikeCount = ImageTitleButton.Model()
        dislikeCount.activityIndicatorColor = JokesStyle.instance.jokeCellModel.dislikeCountActivityColor()
        dislikeCount.image = CompoundImage(null, null, JokesStyle.instance.jokeCellModel.dislikeCountImage())
        dislikeCount.imageTintColor = JokesStyle.instance.jokeCellModel.unselectedDislikeCountTintColor()
        dislikeCount.backgroundColor = JokesStyle.instance.jokeCellModel.unselectedDislikeCountBackgroundColor()
        dislikeCount.title = joke.dislikeCount.toString().toSpannableString(JokesStyle.instance.jokeCellModel.unselectedDislikeCountSpans())
        dislikeCount.borderRadius = ApplicationConstraints.constant.x12.value
        dislikeCount.isDisabled = true
        dislikeCount.isLoading = false
        return dislikeCount
    }

    private fun compoundImage(url: String?, placeholder: Drawable?): CompoundImage {
        if (!url.isNullOrEmpty()) {
            return CompoundImage(url, null, null, ImageView.ScaleType.CENTER_CROP)
        }
        return CompoundImage(null, null, placeholder, ImageView.ScaleType.CENTER_CROP)
    }
    
    private fun time(createdAt: String?): String? {
        if (createdAt.isNullOrEmpty()) {
            return null
        }
        return this.timeDateFormat.parse(createdAt)?.let { DateUtils.getRelativeTimeSpanString(it.time, Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString() }
    }

    private fun usernameText(user: User?): String? {
        val username = user?.username
        if (username != null) {
            return JokesApplicationLocalization.instance.usernameTitle(username)
        }
        return null
    }
}
