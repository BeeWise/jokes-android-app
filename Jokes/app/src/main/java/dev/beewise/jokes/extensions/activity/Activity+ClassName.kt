package dev.beewise.jokes.extensions.activity

import android.app.Activity

public fun Activity.className(): String {
    return this::class.java.name
}