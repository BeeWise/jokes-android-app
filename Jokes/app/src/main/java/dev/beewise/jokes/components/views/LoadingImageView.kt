package dev.beewise.jokes.components.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

public interface LoadingImageViewInterface {
    fun setLoading(isLoading: Boolean)
    fun setImage(image: Bitmap?, scaleType: ImageView.ScaleType)
}

class LoadingImageView: ConstraintLayout, LoadingImageViewInterface {
    var imageView: ShapeableImageView? = null
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
        this.setupImageView()
        this.setupProgressBar()
    }

    private fun setupImageView() {
        val imageView = ShapeableImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        imageView.adjustViewBounds = true
        this.addView(imageView)
        this.imageView = imageView
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
        }
    }

    override fun setLoading(isLoading: Boolean) {
        this.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun setImage(image: Bitmap?, scaleType: ImageView.ScaleType) {
        this.imageView?.setImageBitmap(image)
        this.imageView?.scaleType = scaleType
    }

    fun setImageCornerRadius(topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
        this.imageView?.shapeAppearanceModel?.toBuilder()
            ?.setTopLeftCorner(CornerFamily.ROUNDED, topLeft)
            ?.setTopRightCorner(CornerFamily.ROUNDED, topRight)
            ?.setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeft)
            ?.setBottomRightCorner(CornerFamily.ROUNDED, bottomRight)
            ?.build()?.let {
                this.imageView?.shapeAppearanceModel = it
            }
    }

    fun setImageCornerRadius(radius: Float) {
        this.imageView?.shapeAppearanceModel?.toBuilder()?.setAllCorners(CornerFamily.ROUNDED, radius)?.build()?.let {
            this.imageView?.shapeAppearanceModel = it
        }
    }

    fun setProgressBarColor(color: Int) {
        this.progressBar?.indeterminateTintList = ColorStateList.valueOf(color)
    }
}