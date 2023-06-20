package dev.beewise.jokes.components.buttons

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.text.SpannableString
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import dev.beewise.jokes.models.image.CompoundImage
import dev.beewise.jokes.style.ApplicationConstraints
import dev.beewise.jokes.style.ApplicationStyle

class ImageTitleButton: ConstraintLayout {
    class Model {
        var image: CompoundImage? = null
        var imageTintColor: Int? = null

        var title: SpannableString? = null

        var backgroundImage: CompoundImage? = null

        var borderRadius = ApplicationConstraints.constant.x4.value
        var borderWidth = ApplicationConstraints.constant.x0.value
        var borderColor = ApplicationStyle.instance.primary()

        var activityIndicatorColor = ApplicationStyle.instance.white()
        var backgroundColor = ApplicationStyle.instance.primary()

        var isLoading = false
        var isDisabled = false

        var opacity = 1.0F
    }

    lateinit var backgroundImageView: ImageFilterView
    lateinit var containerView: ConstraintLayout
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var progressBar: ProgressBar

    constructor(context: Context) : super(context) {
        this.setup()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.setup()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.setup()
    }

    private fun setup() {
        this.setupSubviews()
        this.setupSubviewsConstraints()
    }

    private fun setupSubviews() {
        this.setupBackgroundImageView()
        this.setupContainerView()
        this.setupImageView()
        this.setupTextView()
        this.setupProgressBar()
    }

    private fun setupBackgroundImageView() {
        val imageView = ImageFilterView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        imageView.visibility = View.GONE
        this.addView(imageView)
        this.backgroundImageView = imageView
    }

    private fun setupContainerView() {
        val view = ConstraintLayout(this.context)
        view.id = View.generateViewId()
        view.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        this.addView(view)
        this.containerView = view
    }

    private fun setupImageView() {
        val imageView = ImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        this.containerView.addView(imageView)
        this.imageView = imageView
    }

    private fun setupTextView() {
        val textView = TextView(this.context)
        textView.id = View.generateViewId()
        textView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textView.setLines(1)
        this.containerView.addView(textView)
        this.textView = textView
    }

    private fun setupProgressBar() {
        val progressBar = ProgressBar(this.context)
        progressBar.id = View.generateViewId()
        progressBar.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.INVISIBLE
        this.containerView.addView(progressBar)
        this.progressBar = progressBar
    }

    private fun setupSubviewsConstraints() {
        this.setupBackgroundImageViewConstraints()
        this.setupContainerViewConstraints()
        this.setupImageViewConstraints()
        this.setupTextViewConstraints()
        this.setupProgressBarConstraints()
    }

    private fun setupBackgroundImageViewConstraints() {
        this.backgroundImageView.updateLayoutParams<LayoutParams> {
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
        }
    }

    private fun setupContainerViewConstraints() {
        this.containerView.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID
        }
    }

    private fun setupImageViewConstraints() {
        this.imageView.updateLayoutParams<LayoutParams> {
            this.startToStart = LayoutParams.PARENT_ID
            this.endToStart = this@ImageTitleButton.textView.id
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID

            this.dimensionRatio = "1:1"
            this.matchConstraintPercentHeight = 0.62F
        }
    }

    private fun setupTextViewConstraints() {
        this.textView.updateLayoutParams<LayoutParams> {
            this.startToEnd = this@ImageTitleButton.imageView.id
            this.endToEnd = LayoutParams.PARENT_ID
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID

            this.marginStart = ApplicationConstraints.constant.x8.value
        }
    }

    private fun setupProgressBarConstraints() {
        this.progressBar.updateLayoutParams<LayoutParams> {
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
        }
    }

    fun setModel(model: Model) {
        this.alpha = model.opacity

        val drawable = GradientDrawable()
        drawable.color = ColorStateList.valueOf(model.backgroundColor)
        drawable.cornerRadius = model.borderRadius.toFloat()
        drawable.setStroke(model.borderWidth, model.borderColor)
        this.background = drawable

        if (model.backgroundImage != null) {
            this.backgroundImageView.setImageDrawable(model.backgroundImage?.drawable)
            this.backgroundImageView.scaleType = model.backgroundImage?.scaleType
            this.backgroundImageView.visibility = View.VISIBLE
            this.backgroundImageView.round = model.borderRadius.toFloat()
        } else {
            this.backgroundImageView.visibility = View.GONE
        }

        this.progressBar.indeterminateTintList = ColorStateList.valueOf(model.activityIndicatorColor)
        this.textView.text = model.title

        this.imageView.setImageDrawable(model.image?.drawable)
        this.imageView.scaleType = model.image?.scaleType
        this.imageView.imageTintList = model.imageTintColor?.let { ColorStateList.valueOf(it) }

        this.setIsLoading(model.isLoading)
    }

    private fun setIsLoading(isLoading: Boolean) {
        if (isLoading) {
            this.textView.visibility = View.INVISIBLE
            this.progressBar.visibility = View.VISIBLE
        } else {
            this.progressBar.visibility = View.INVISIBLE
            this.textView.visibility = View.VISIBLE
        }
    }
}