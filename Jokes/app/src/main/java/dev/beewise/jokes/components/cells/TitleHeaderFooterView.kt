package dev.beewise.jokes.components.cells

import android.text.SpannableString
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R
import java.lang.ref.WeakReference

interface TitleHeaderFooterViewDelegate {
    fun titleHeaderFooterView(view: TitleHeaderFooterView?, didSelectTextView: TextView?)
}

class TitleHeaderFooterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var delegate: WeakReference<TitleHeaderFooterViewDelegate>? = null
    var textView: TextView? = null

    init {
        this.setupTextView()
    }

    private fun setupTextView() {
        this.textView = this.itemView.findViewById(R.id.textViewId)
        this.textView?.setOnClickListener {
            this.delegate?.get()?.titleHeaderFooterView(this, this.textView)
        }
    }

    public fun setSpannableString(text: SpannableString?) {
        this.textView?.text = text
    }
}