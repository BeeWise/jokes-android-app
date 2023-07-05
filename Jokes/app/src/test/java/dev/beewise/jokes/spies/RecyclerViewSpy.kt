package dev.beewise.jokes.spies

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSpy(context: Context) : RecyclerView(context) {
    var scrollToPositionCalled: Boolean = false

    override fun scrollToPosition(position: Int) {
        this.scrollToPositionCalled = true
        super.scrollToPosition(position)
    }
}