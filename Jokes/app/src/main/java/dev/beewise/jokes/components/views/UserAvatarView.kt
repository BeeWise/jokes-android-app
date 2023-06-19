package dev.beewise.jokes.components.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import dev.beewise.jokes.style.ApplicationConstraints
import java.lang.ref.WeakReference

interface UserAvatarViewDelegate {
    fun userAvatarViewOnPress(view: UserAvatarView)
}

class UserAvatarView: ConstraintLayout {
    class Model(var loadingImage: LoadingImageView.Model) {
        var isDisabled: Boolean = false
        var backgroundColor: Int? = null
        var borderColor: Int? = null
        var borderWidth: Int? = null
        var margin = ApplicationConstraints.constant.x0.value
    }

    private lateinit var imageView: LoadingImageView

    var delegate: WeakReference<UserAvatarViewDelegate>? = null

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
    }

    private fun setupView() {
        this.setOnClickListener {
            this.delegate?.get()?.userAvatarViewOnPress(this)
        }
    }

    private fun setupImageView() {
        val imageView = LoadingImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        this.addView(imageView)
        this.imageView = imageView
    }

    private fun setupSubviewsConstraints() {
        this.setupImageViewConstraints()
    }

    private fun setupImageViewConstraints() {
        this.imageView.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID

            this.dimensionRatio = "1:1"
        }
    }

    fun setModel(model: Model) {
        this.isClickable = model.isDisabled

        val drawable = GradientDrawable()
        drawable.color = model.backgroundColor?.let { ColorStateList.valueOf(it) }
        drawable.cornerRadius = model.loadingImage.borderRadius
        model.borderWidth?.let { model.borderColor?.let { it1 -> drawable.setStroke(it, it1) } }
        this.background = drawable

        this.imageView.setModel(model.loadingImage)
        this.imageView.updateLayoutParams<LayoutParams> {
            this.marginStart = model.margin
            this.marginEnd = model.margin
            this.topMargin = model.margin
            this.bottomMargin = model.margin
        }
    }
}