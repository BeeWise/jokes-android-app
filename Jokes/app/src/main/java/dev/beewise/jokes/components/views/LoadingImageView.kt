package dev.beewise.jokes.components.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ProgressBar
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import dev.beewise.jokes.models.image.CompoundImage
import java.lang.ref.WeakReference

interface LoadingImageViewInterface {
    fun setIsLoadingImage(isLoadingImage: Boolean)
    fun setImage(image: CompoundImage)
}

class LoadingImageView: ConstraintLayout, LoadingImageViewInterface {
    class Model(var image: CompoundImage, var isLoading: Boolean) {
        var activityIndicatorColor: Int? = null
        var imageBackgroundColor: Int? = null
        var borderRadius: Float = 0F
        var viewInterface: WeakReference<LoadingImageViewInterface>? = null
    }

    var imageView: ImageFilterView? = null
    var progressBar: ProgressBar? = null

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
        this.setupView()
        this.setupImageView()
        this.setupProgressBar()
    }

    private fun setupView() {
        this.outlineProvider = ViewOutlineProvider.BACKGROUND
        this.clipToOutline = true
    }

    private fun setupImageView() {
        val imageView = ImageFilterView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        this.addView(imageView)
        this.imageView = imageView
    }

    private fun setupProgressBar() {
        val progressBar = ProgressBar(this.context)
        progressBar.id = View.generateViewId()
        progressBar.layoutParams = LayoutParams(0, 0)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.INVISIBLE
        this.addView(progressBar)
        this.progressBar = progressBar
    }

    private fun setupSubviewsConstraints() {
        this.setupImageViewConstraints()
        this.setupProgressBarConstraints()
    }

    private fun setupImageViewConstraints() {
        this.imageView?.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID
        }
    }

    private fun setupProgressBarConstraints() {
        this.progressBar?.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID

            this.dimensionRatio = "1:1"
            this.matchConstraintPercentHeight = 0.62F
        }
    }

    fun setModel(model: Model) {
        model.viewInterface = WeakReference(this)

        this.setIsLoadingImage(model.isLoading)
        this.setImage(model.image)

        this.progressBar?.indeterminateTintList = model.activityIndicatorColor?.let { ColorStateList.valueOf(it) }
        this.imageView?.round = model.borderRadius

        if (model.imageBackgroundColor != null) {
            this.imageView?.setBackgroundColor(model.imageBackgroundColor!!)
        }
    }

    override fun setImage(image: CompoundImage) {
        if (image.bitmap != null) {
            this.imageView?.setImageBitmap(image.bitmap)
        } else if (image.drawable != null) {
            this.imageView?.setImageDrawable(image.drawable)
        }
    }

    override fun setIsLoadingImage(isLoadingImage: Boolean) {
        this.progressBar?.visibility = if (isLoadingImage) View.VISIBLE else View.INVISIBLE
        this.imageView?.visibility = if (isLoadingImage) View.INVISIBLE else View.VISIBLE
    }
}