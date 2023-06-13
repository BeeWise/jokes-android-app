package dev.beewise.jokes.style

import dev.beewise.jokes.managers.ContextManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import dev.beewise.jokes.R
import kotlin.math.roundToInt

class ApplicationStyle {
    companion object {
        val instance = ApplicationStyle()
    }

    fun context(): Context? = ContextManager.instance.context?.get()

    // region Colors
    public fun white(): Int { return R.color.white.toColor() }
    public fun black(): Int { return R.color.black.toColor() }
    public fun primary(): Int { return R.color.primary.toColor() }
    public fun secondary(): Int { return R.color.secondary.toColor() }

    public fun lightPrimary(): Int { return R.color.lightPrimary.toColor() }
    public fun lightPrimaryShade15(): Int { return R.color.lightPrimaryShade15.toColor() }

    public fun lightSecondary(): Int { return R.color.lightSecondary.toColor() }
    public fun lightSecondaryShade15(): Int { return R.color.lightSecondaryShade15.toColor() }

    public fun transparent(): Int { return R.color.transparent.toColor() }

    public fun gray(): Int { return R.color.gray.toColor() }
    public fun lightGray(): Int { return R.color.lightGray.toColor() }

    public fun backgroundColor(): Int { return R.color.backgroundColor.toColor() }

    public fun neonDarkGreen(): Int { return R.color.neonDarkGreen.toColor() }
    public fun neonOrange(): Int { return R.color.neonOrange.toColor() }
    public fun neonPurple(): Int { return R.color.neonPurple.toColor() }
    // endregion
    
    // region Images
    public enum class Images(val value: Int) {
//        logoSmallImage(R.mipmap.logo_small_image);
    }

//    public inline fun <reified T> logoSmallImage(type: ImageType): T = this.dev.beewise.jokes.style.image(Images.logoSmallImage.value, type)

    public enum class ImageType(val value: Int) {
        drawable(0),
        bitmap(1),
        id(2)
    }
    // endregion

    // region Fonts
    public enum class Fonts(val value: Int) {
//        regular(R.font.europa_regular),
//        bold(R.font.europa_bold),
//        light(R.font.europa_light)
    }

//    public val regular = this.dev.beewise.jokes.style.getFont(Fonts.regular.value)
//    public val bold = this.dev.beewise.jokes.style.getFont(Fonts.bold.value)
//    public val light = this.dev.beewise.jokes.style.getFont(Fonts.light.value)
    // endregion
}

fun ApplicationStyle.getFont(id: Int): Typeface {
    return this.context()?.let {
        return ResourcesCompat.getFont(it, id) ?: Typeface.DEFAULT
    } ?: Typeface.DEFAULT
}

fun ApplicationStyle.getColor(id: Int): Int {
    return this.context()?.let {
        return ContextCompat.getColor(it, id)
    } ?: -1
}

fun ApplicationStyle.getDrawable(id: Int): Drawable {
    return this.context()?.let {
        return ContextCompat.getDrawable(it, id) ?: ColorDrawable(Color.TRANSPARENT)
    } ?: ColorDrawable(Color.TRANSPARENT)
}

fun ApplicationStyle.getBitmap(id: Int): Bitmap {
    return this.context()?.let {
        return BitmapFactory.decodeResource(it.resources, id)
    } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
}

inline fun <reified T> ApplicationStyle.image(id: Int, type: ApplicationStyle.ImageType): T {
    return when (type) {
        ApplicationStyle.ImageType.drawable -> this.getDrawable(id) as T
        ApplicationStyle.ImageType.bitmap -> this.getBitmap(id) as T
        ApplicationStyle.ImageType.id -> id as T
    }
}

fun Int.toColor(): Int {
    val context = ContextManager.instance.context?.get() ?: return Color.TRANSPARENT
    return ContextCompat.getColor(context, this)
}

fun Int.withAlpha(alpha: Float): Int {
    val value = (alpha * 255).roundToInt()
    return ColorUtils.setAlphaComponent(this, value)
}