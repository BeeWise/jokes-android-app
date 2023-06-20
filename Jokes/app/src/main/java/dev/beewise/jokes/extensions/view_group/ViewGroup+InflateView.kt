package dev.beewise.jokes.extensions.view_group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflateView(viewId: Int): View {
    val inflater = LayoutInflater.from(this.context)
    return inflater.inflate(viewId, this, false)
}