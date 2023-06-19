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

        // TODO: - Add logic for setting up the background image!
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
        this.setupImageView()
        this.setupTextView()
        this.setupProgressBar()
    }

    private fun setupImageView() {
        val imageView = ImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        imageView.adjustViewBounds = true
        this.addView(imageView)
        this.imageView = imageView
    }

    private fun setupTextView() {
        val textView = TextView(this.context)
        textView.id = View.generateViewId()
        textView.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT)
        textView.setLines(1)
        this.addView(textView)
        this.textView = textView
    }

    private fun setupProgressBar() {
        val progressBar = ProgressBar(this.context)
        progressBar.id = View.generateViewId()
        progressBar.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.INVISIBLE
        this.addView(progressBar)
        this.progressBar = progressBar
    }

    private fun setupSubviewsConstraints() {
        this.setupImageViewConstraints()
        this.setupTextViewConstraints()
        this.setupProgressBarConstraints()
    }

    private fun setupImageViewConstraints() {
        this.imageView.updateLayoutParams<LayoutParams> {
            this.startToStart = LayoutParams.PARENT_ID
            this.endToStart = this@ImageTitleButton.textView.id
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID

            this.marginStart = ApplicationConstraints.constant.x10.value
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

            this.marginStart = ApplicationConstraints.constant.x10.value
            this.marginEnd = ApplicationConstraints.constant.x10.value
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