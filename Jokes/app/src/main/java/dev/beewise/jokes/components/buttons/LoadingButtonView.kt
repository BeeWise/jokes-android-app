package dev.beewise.jokes.components.buttons

import android.content.Context
import android.content.res.ColorStateList
import android.text.SpannableString
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import dev.beewise.jokes.style.ApplicationConstraints
import dev.beewise.jokes.style.ApplicationStyle

class LoadingButtonView: ConstraintLayout {
    var textView: TextView? = null
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
        this.setupTextView()
        this.setupProgressBar()
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
        progressBar.indeterminateTintList = ColorStateList.valueOf(ApplicationStyle.instance.primary())
        progressBar.visibility = View.INVISIBLE
        this.addView(progressBar)
        this.progressBar = progressBar
    }

    private fun setupSubviewsConstraints() {
        this.setupTextViewConstraints()
        this.setupProgressBarConstraints()
    }

    private fun setupTextViewConstraints() {
        this.textView?.updateLayoutParams<LayoutParams> {
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
            this.marginStart = ApplicationConstraints.constant.x8.value
            this.marginEnd = ApplicationConstraints.constant.x8.value
        }
    }

    private fun setupProgressBarConstraints() {
        this.progressBar?.updateLayoutParams<LayoutParams> {
            this.startToStart = LayoutParams.PARENT_ID
            this.endToEnd = LayoutParams.PARENT_ID
            this.topToTop = LayoutParams.PARENT_ID
            this.bottomToBottom = LayoutParams.PARENT_ID
        }
    }

    fun setTitle(title: SpannableString?) {
        this.textView?.text = title
    }

    fun setIsLoading(isLoading: Boolean) {
        if (isLoading) {
            this.textView?.visibility = View.INVISIBLE
            this.progressBar?.visibility = View.VISIBLE
        } else {
            this.progressBar?.visibility = View.INVISIBLE
            this.textView?.visibility = View.VISIBLE
        }
    }
}