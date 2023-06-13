package dev.beewise.jokes.extensions.fragment

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment

public fun Fragment?.runOnUiThread(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post {
        action.invoke()
    }
}