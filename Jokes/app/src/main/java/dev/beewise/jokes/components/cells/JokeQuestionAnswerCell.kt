package dev.beewise.jokes.components.cells

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R
import dev.beewise.jokes.application.JokesApplicationLocalization
import dev.beewise.jokes.components.buttons.ImageTitleButton
import dev.beewise.jokes.components.views.UserAvatarView
import dev.beewise.jokes.components.views.UserAvatarViewDelegate
import dev.beewise.jokes.extensions.font_span.FontSpan
import dev.beewise.jokes.models.image.CompoundImage
import dev.beewise.jokes.style.ApplicationConstraints
import dev.beewise.jokes.style.ApplicationStyle
import toSpannableString
import java.lang.ref.WeakReference

interface JokeQuestionAnswerCellDelegate {
    fun jokeQuestionAnswerCellOnPressLikeCount(cell: JokeQuestionAnswerCell, id: String?)
    fun jokeQuestionAnswerCellOnPressDislikeCount(cell: JokeQuestionAnswerCell, id: String?)
    fun jokeQuestionAnswerCellOnPressUserAvatar(cell: JokeQuestionAnswerCell, id: String?)
    fun jokeQuestionAnswerCellOnPressUserName(cell: JokeQuestionAnswerCell, id: String?)
    fun jokeQuestionAnswerCellOnPressReadAnswer(cell: JokeQuestionAnswerCell, id: String?)
}

class JokeQuestionAnswerCell(itemView: View) : RecyclerView.ViewHolder(itemView), UserAvatarViewDelegate {
    class Model(var avatar: UserAvatarView.Model) {
        var id: String? = null

        var name: SpannableString? = null
        var username: SpannableString? = null
        var text: SpannableString? = null
        var answer: SpannableString? = null
        var isRead: Boolean = false

        var likeCount: ImageTitleButton.Model? = null
        var dislikeCount: ImageTitleButton.Model? = null

        var time: SpannableString? = null
    }

    var cellModel: Model? = null
    var delegate: WeakReference<JokeQuestionAnswerCellDelegate>? = null

    lateinit var contentView: ConstraintLayout
    lateinit var containerView: ConstraintLayout

    lateinit var topContainerView: ConstraintLayout
    lateinit var userAvatarView: UserAvatarView
    lateinit var userContainerView: ConstraintLayout
    lateinit var nameTextView: TextView
    lateinit var usernameTextView: TextView

    lateinit var textView: TextView

    lateinit var answerContainerView: ConstraintLayout
    lateinit var answerTextView: TextView
    lateinit var answerButton: ImageTitleButton

    lateinit var bottomContainerView: ConstraintLayout
    lateinit var likeCountView: ImageTitleButton
    lateinit var dislikeCountView: ImageTitleButton
    lateinit var timeTextView: TextView

    init {
        this.setupSubviews()
        this.setupSubviewsConstraints()
    }

    private fun setupSubviews() {
        this.setupItemView()
        this.setupContentView()
        this.setupContainerView()
        this.setupTopContainerView()
        this.setupUserAvatarView()
        this.setupUserContainerView()
        this.setupNameTextView()
        this.setupUsernameTextView()
        this.setupTextView()
        this.setupAnswerContainerView()
        this.setupAnswerTextView()
        this.setupAnswerButton()
        this.setupBottomContainerView()
        this.setupLikeCountView()
        this.setupDislikeCountView()
        this.setupTimeTextView()
    }

    private fun setupItemView() {
        this.itemView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setupContentView() {
        this.contentView = this.itemView.findViewById(R.id.contentViewId)
        this.contentView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setupContainerView() {
        val drawable = GradientDrawable()
        drawable.color = ColorStateList.valueOf(ApplicationStyle.instance.white())
        drawable.cornerRadius = ApplicationConstraints.constant.x16.value.toFloat()
        drawable.setStroke(ApplicationConstraints.constant.x1.value, ApplicationStyle.instance.lightGray())

        val view = ConstraintLayout(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        view.background = drawable
        this.contentView.addView(view)
        this.containerView = view
    }

    private fun setupTopContainerView() {
        val view = ConstraintLayout(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.containerView.addView(view)
        this.topContainerView = view
    }

    private fun setupUserAvatarView() {
        val view = UserAvatarView(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(0, 0)
        view.delegate = WeakReference(this)
        this.topContainerView.addView(view)
        this.userAvatarView = view
    }

    override fun userAvatarViewOnPress(view: UserAvatarView) {
        this.delegate?.get()?.jokeQuestionAnswerCellOnPressUserAvatar(this, this.cellModel?.id)
    }

    private fun setupUserContainerView() {
        val view = ConstraintLayout(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        view.setOnClickListener {
            this.delegate?.get()?.jokeQuestionAnswerCellOnPressUserName(this, this.cellModel?.id)
        }
        this.topContainerView.addView(view)
        this.userContainerView = view
    }

    private fun setupNameTextView() {
        val textView = TextView(this.itemView.context)
        textView.id = View.generateViewId()
        textView.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.userContainerView.addView(textView)
        this.nameTextView = textView
    }

    private fun setupUsernameTextView() {
        val textView = TextView(this.itemView.context)
        textView.id = View.generateViewId()
        textView.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.userContainerView.addView(textView)
        this.usernameTextView = textView
    }

    private fun setupTextView() {
        val textView = TextView(this.itemView.context)
        textView.id = View.generateViewId()
        textView.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.containerView.addView(textView)
        this.textView = textView
    }

    private fun setupAnswerContainerView() {
        val view = ConstraintLayout(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.containerView.addView(view)
        this.answerContainerView = view
    }

    private fun setupAnswerTextView() {
        val textView = TextView(this.itemView.context)
        textView.id = View.generateViewId()
        textView.visibility = View.GONE
        textView.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.answerContainerView.addView(textView)
        this.answerTextView = textView
    }

    private fun setupAnswerButton() {
        val view = ImageTitleButton(this.itemView.context)
        view.id = View.generateViewId()
        view.visibility = View.GONE
        view.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, 0)
        view.setOnClickListener {
            this.delegate?.get()?.jokeQuestionAnswerCellOnPressReadAnswer(this, this.cellModel?.id)
        }
        view.setModel(this.setupAnswerButtonModel())
        this.answerContainerView.addView(view)
        this.answerButton = view
    }

    private fun setupAnswerButtonModel(): ImageTitleButton.Model {
        val spans = arrayListOf<Any>(
            ForegroundColorSpan(ApplicationStyle.instance.white()),
            FontSpan(ApplicationStyle.instance.regular()),
            AbsoluteSizeSpan(17, true)
        )
        val model = ImageTitleButton.Model()
        model.image = CompoundImage(null, null, ApplicationStyle.instance.answerSmallImage(ApplicationStyle.ImageType.drawable))
        model.isDisabled = false
        model.backgroundImage = CompoundImage(null, null, ApplicationStyle.instance.buttonBackgroundImage(ApplicationStyle.ImageType.drawable))
        model.borderRadius = ApplicationConstraints.constant.x16.value
        model.borderColor = ApplicationStyle.instance.white()
        model.backgroundColor = ApplicationStyle.instance.primary()
        model.title = JokesApplicationLocalization.instance.readAnswerTitle().toSpannableString(spans)
        return model
    }

    private fun setupBottomContainerView() {
        val view = ConstraintLayout(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.containerView.addView(view)
        this.bottomContainerView = view
    }

    private fun setupLikeCountView() {
        val view = ImageTitleButton(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, 0)
        view.setOnClickListener {
            this.delegate?.get()?.jokeQuestionAnswerCellOnPressLikeCount(this, this.cellModel?.id)
        }
        this.bottomContainerView.addView(view)
        this.likeCountView = view
    }

    private fun setupDislikeCountView() {
        val view = ImageTitleButton(this.itemView.context)
        view.id = View.generateViewId()
        view.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, 0)
        view.setOnClickListener {
            this.delegate?.get()?.jokeQuestionAnswerCellOnPressDislikeCount(this, this.cellModel?.id)
        }
        this.bottomContainerView.addView(view)
        this.dislikeCountView = view
    }

    private fun setupTimeTextView() {
        val textView = TextView(this.itemView.context)
        textView.id = View.generateViewId()
        textView.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        this.topContainerView.addView(textView)
        this.usernameTextView = textView
    }

    private fun setupSubviewsConstraints() {
        this.setupContainerViewConstraints()
        this.setupTopContainerViewConstraints()
        this.setupUserAvatarViewConstraints()
        this.setupUserContainerViewConstraints()
        this.setupNameTextViewConstraints()
        this.setupUsernameTextViewConstraints()
        this.setupTextViewConstraints()
        this.setupAnswerContainerViewConstraints()
        this.setupAnswerTextViewConstraints()
        this.setupAnswerButtonConstraints()
        this.setupBottomContainerViewConstraints()
        this.setupLikeCountViewConstraints()
        this.setupDislikeCountViewConstraints()
        this.setupTimeTextViewConstraints()
    }

    private fun setupContainerViewConstraints() {
        this.containerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

            this.marginStart = ApplicationConstraints.constant.x16.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupTopContainerViewConstraints() {
        this.topContainerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToTop = this@JokeQuestionAnswerCell.textView.id
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

            this.marginStart = ApplicationConstraints.constant.x16.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
            this.topMargin = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupUserAvatarViewConstraints() {
        this.userAvatarView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToStart = this@JokeQuestionAnswerCell.userContainerView.id

            this.width = ApplicationConstraints.constant.x40.value
            this.height = ApplicationConstraints.constant.x40.value
        }
    }

    private fun setupUserContainerViewConstraints() {
        this.userContainerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToEnd = this@JokeQuestionAnswerCell.userAvatarView.id

            this.marginStart = ApplicationConstraints.constant.x8.value
        }
    }

    private fun setupNameTextViewConstraints() {
        this.nameTextView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToTop = this@JokeQuestionAnswerCell.usernameTextView.id
        }
    }

    private fun setupUsernameTextViewConstraints() {
        this.usernameTextView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToBottom = this@JokeQuestionAnswerCell.nameTextView.id
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }

    private fun setupTextViewConstraints() {
        this.textView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToBottom = this@JokeQuestionAnswerCell.topContainerView.id
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToTop = this@JokeQuestionAnswerCell.answerContainerView.id

            this.marginStart = ApplicationConstraints.constant.x16.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
            this.topMargin = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupAnswerContainerViewConstraints() {
        this.answerContainerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToBottom = this@JokeQuestionAnswerCell.textView.id
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToTop = this@JokeQuestionAnswerCell.bottomContainerView.id

            this.marginStart = ApplicationConstraints.constant.x16.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
            this.topMargin = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupAnswerTextViewConstraints() {
        this.answerTextView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }

    private fun setupAnswerButtonConstraints() {
        this.answerButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

            this.height = ApplicationConstraints.constant.x32.value
        }
    }

    private fun setupBottomContainerViewConstraints() {
        this.bottomContainerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToBottom = this@JokeQuestionAnswerCell.answerContainerView.id
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

            this.marginStart = ApplicationConstraints.constant.x16.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
            this.topMargin = ApplicationConstraints.constant.x16.value
            this.bottomMargin = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupLikeCountViewConstraints() {
        this.likeCountView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToStart = this@JokeQuestionAnswerCell.dislikeCountView.id

            this.height = ApplicationConstraints.constant.x24.value

            this.marginEnd = ApplicationConstraints.constant.x8.value
        }
    }

    private fun setupDislikeCountViewConstraints() {
        this.likeCountView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.startToEnd = this@JokeQuestionAnswerCell.likeCountView.id
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

            this.height = ApplicationConstraints.constant.x24.value
        }
    }

    private fun setupTimeTextViewConstraints() {
        this.timeTextView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }

    fun setModel(model: Model) {
        this.cellModel = model

        this.userAvatarView.setModel(model.avatar)
        this.nameTextView.text = model.name
        this.usernameTextView.text = model.username
        this.textView.text = model.text
        this.answerTextView.text = model.answer
        model.likeCount?.let { this.likeCountView.setModel(it) }
        model.dislikeCount?.let { this.dislikeCountView.setModel(it) }
        this.timeTextView.text = model.time

        this.answerTextView.visibility = if (model.isRead) View.VISIBLE else View.GONE
        this.answerButton.visibility = if (model.isRead) View.GONE else View.VISIBLE
    }
}