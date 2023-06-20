package dev.beewise.jokes.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import dev.beewise.jokes.style.ApplicationConstraints
import dev.beewise.jokes.style.ApplicationStyle
import java.lang.ref.WeakReference

interface LogoNavigationViewDelegate {
    fun logoNavigationViewOnPressBackButton(view: LogoNavigationView)
    fun logoNavigationViewOnPressLogoImage(view: LogoNavigationView)
    fun logoNavigationViewOnPressUserAvatar(view: LogoNavigationView)
}

class LogoNavigationView: ConstraintLayout, UserAvatarViewDelegate {
    class Model(var userAvatar: UserAvatarView.Model) {
        var includeBack: Boolean = false
        var includeUserAvatar: Boolean = false
        var includeSeparator: Boolean = false
    }

    var backButton: ImageButton? = null
    var logoImageView: ImageView? = null
    var userAvatarView: UserAvatarView? = null
    var separatorView: View? = null

    var delegate: WeakReference<LogoNavigationViewDelegate>? = null

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
        this.setupBackButton()
        this.setupLogoImageView()
        this.setupUserAvatarView()
        this.setupSeparatorView()
    }

    private fun setupBackButton() {
        val button = ImageButton(this.context)
        button.id = View.generateViewId()
        button.layoutParams = LayoutParams(0, 0)
        button.setImageDrawable(ApplicationStyle.instance.backArrowSmallImage(ApplicationStyle.ImageType.drawable))
        button.setOnClickListener {
            this.delegate?.get()?.logoNavigationViewOnPressBackButton(this)
        }
        button.visibility = View.GONE
        this.addView(button)
        this.backButton = button
    }

    private fun setupLogoImageView() {
        val imageView = ImageView(this.context)
        imageView.id = View.generateViewId()
        imageView.layoutParams = LayoutParams(0, 0)
        imageView.adjustViewBounds = true
        imageView.setImageDrawable(ApplicationStyle.instance.neonLogoMediumImage(ApplicationStyle.ImageType.drawable))
        imageView.setOnClickListener {
            this.delegate?.get()?.logoNavigationViewOnPressLogoImage(this)
        }
        this.addView(imageView)
        this.logoImageView = imageView
    }

    private fun setupUserAvatarView() {
        val userAvatarView = UserAvatarView(this.context)
        userAvatarView.id = View.generateViewId()
        userAvatarView.layoutParams = LayoutParams(0, 0)
        userAvatarView.delegate = WeakReference(this)
        userAvatarView.visibility = View.GONE
        this.addView(userAvatarView)
        this.userAvatarView = userAvatarView
    }

    override fun userAvatarViewOnPress(view: UserAvatarView) {
        this.delegate?.get()?.logoNavigationViewOnPressUserAvatar(this)
    }

    private fun setupSeparatorView() {
        val view = View(this.context)
        view.id = View.generateViewId()
        view.setBackgroundColor(ApplicationStyle.instance.white())
        view.visibility = View.GONE
        this.addView(view)
        this.separatorView = view
    }
    
    private fun setupSubviewsConstraints() {
        this.setupBackButtonConstraints()
        this.setupLogoImageViewConstraints()
        this.setupUserAvatarViewConstraints()
        this.setupSeparatorViewConstraints()
    }

    private fun setupBackButtonConstraints() {
        this.backButton?.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID

            this.dimensionRatio = "1:1"
            this.height = ApplicationConstraints.constant.x40.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupLogoImageViewConstraints() {
        this.logoImageView?.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID

            this.matchConstraintPercentHeight = 0.75F
            this.marginStart = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupUserAvatarViewConstraints() {
        this.userAvatarView?.updateLayoutParams<LayoutParams> {
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID

            this.dimensionRatio = "1:1"
            this.height = ApplicationConstraints.constant.x40.value
            this.marginEnd = ApplicationConstraints.constant.x16.value
        }
    }

    private fun setupSeparatorViewConstraints() {
        this.separatorView?.updateLayoutParams<LayoutParams> {
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID

            this.height = ApplicationConstraints.constant.x1.value
        }
    }
    
    fun setModel(model: Model) {
        this.userAvatarView?.setModel(model.userAvatar)

        this.backButton?.visibility = if (model.includeBack) View.VISIBLE else View.GONE
        this.backButton?.isClickable = model.includeBack

        this.userAvatarView?.visibility = if (model.includeUserAvatar) View.VISIBLE else View.GONE
        this.userAvatarView?.isClickable = model.includeUserAvatar

        this.separatorView?.visibility = if (model.includeSeparator) View.VISIBLE else View.GONE
    }
}
