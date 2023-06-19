package dev.beewise.jokes.components.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import dev.beewise.jokes.models.image.CompoundImage

class LoadingImageView: ConstraintLayout {
    class Model(var image: CompoundImage, var isLoading: Boolean) {
        var activityIndicatorColor: Int? = null
        var imageBackgroundColor: Int? = null
        var borderRadius: Float = 0F
    }

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

    fun setModel(model: Model) {
        this.progressBar?.visibility = if (model.isLoading) View.VISIBLE else View.INVISIBLE
        this.progressBar?.indeterminateTintList = model.activityIndicatorColor?.let { ColorStateList.valueOf(it) }

        this.setImage(model.image, model.borderRadius)
        this.imageView?.scaleType = model.image.scaleType

        if (model.imageBackgroundColor != null) {
            this.imageView?.setBackgroundColor(model.imageBackgroundColor!!)
        }
    }

    private fun setImage(model: CompoundImage, radius: Float) {
        if (model.drawable != null) {
            this.imageView?.setImageDrawable(model.drawable)
            this.imageView?.shapeAppearanceModel?.toBuilder()?.setAllCorners(CornerFamily.ROUNDED, radius)?.build()?.let { this.imageView?.shapeAppearanceModel = it }
        } else if (model.bitmap != null) {
            this.imageView?.setImageBitmap(model.bitmap)
            this.imageView?.shapeAppearanceModel?.toBuilder()?.setAllCorners(CornerFamily.ROUNDED, radius)?.build()?.let { this.imageView?.shapeAppearanceModel = it }
        } else if (!model.url.isNullOrEmpty()) {
            this.imageView?.load(model.url) {
                Modifier.clip(RoundedCornerShape(radius))
            }
        }
    }
}