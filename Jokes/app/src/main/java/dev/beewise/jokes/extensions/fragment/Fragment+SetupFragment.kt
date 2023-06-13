package dev.beewise.jokes.extensions.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.setupFragment(fragment: Fragment, tag: String, layoutId: Int, bundle: Bundle? = null): Fragment? {
    if (this.childFragmentManager.findFragmentByTag(tag) != null) {
        bundle?.let {
            return this.getFragment(tag, layoutId, it) ?: this.findFragment(tag, layoutId)
        } ?: kotlin.run {
            return this.findFragment(tag, layoutId)
        }
    } else {
        return this.replaceFragment(fragment, tag, layoutId)
    }
}

private fun Fragment.getFragment(tag: String, layoutId: Int, bundle: Bundle): Fragment? {
    this.childFragmentManager.getFragment(bundle, tag)?.let { fragment ->
        return this.replaceFragment(fragment, tag, layoutId)
    }
    return null
}

private fun Fragment.findFragment(tag: String, layoutId: Int): Fragment? {
    this.childFragmentManager.findFragmentByTag(tag)?.let {
        return this.replaceFragment(it, tag, layoutId)
    }
    return null
}

fun Fragment.replaceFragment(fragment: Fragment, tag: String, layoutId: Int): Fragment {
    this.childFragmentManager.beginTransaction().replace(layoutId, fragment, tag).commit()
    return fragment
}