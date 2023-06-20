package dev.beewise.jokes.components.cells

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R

class LoadingHeaderFooterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var progressBar: ProgressBar? = null

    init {
        this.findSubviews()
    }

    private fun findSubviews() {
        this.progressBar = this.itemView.findViewById(R.id.progressBarId)
    }

    public fun setColor(color: Int) {
        val drawable = this.progressBar?.indeterminateDrawable?.mutate()
        drawable?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    public fun setIsLoading(isLoading: Boolean) {
        if (isLoading) {
            this.progressBar?.visibility = View.VISIBLE
        } else {
            this.progressBar?.visibility = View.GONE
        }
    }
}