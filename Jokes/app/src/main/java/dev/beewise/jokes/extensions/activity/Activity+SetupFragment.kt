package dev.beewise.jokes.extensions.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.setupFragment(fragment: Fragment, tag: String, layoutId: Int, bundle: Bundle? = null): Fragment? {
    if (this.supportFragmentManager.findFragmentByTag(tag) != null) {
        bundle?.let {
            return this.getFragment(tag, layoutId, it) ?: this.findFragment(tag, layoutId)
        } ?: kotlin.run {
            return this.findFragment(tag, layoutId)
        }
    } else {
        return this.addFragment(fragment, tag, layoutId)
    }
}

private fun AppCompatActivity.getFragment(tag: String, layoutId: Int, bundle: Bundle): Fragment? {
    this.supportFragmentManager.getFragment(bundle, tag)?.let { fragment ->
        this.supportFragmentManager.beginTransaction().replace(layoutId, fragment, tag).commit()
        return fragment
    }
    return null
}

private fun AppCompatActivity.findFragment(tag: String, layoutId: Int): Fragment? {
    this.supportFragmentManager.findFragmentByTag(tag)?.let {
        this.supportFragmentManager.beginTransaction().replace(layoutId, it, tag).commit()
        return it
    }
    return null
}

private fun AppCompatActivity.addFragment(fragment: Fragment, tag: String, layoutId: Int): Fragment {
    this.supportFragmentManager.beginTransaction().add(layoutId, fragment, tag).commit()
    return fragment
}