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
        wallBackgroundImage(R.mipmap.wall_background_image),
        userAvatarPlaceholderSmallImage(R.mipmap.user_avatar_placeholder_small_image),
        likeSmallImage(R.mipmap.like_small_image),
        dislikeSmallImage(R.mipmap.dislike_small_image),
        backArrowSmallImage(R.mipmap.back_arrow_small_image),
        neonLogoMediumImage(R.mipmap.neon_medium_logo_image),
        answerSmallImage(R.mipmap.answer_small_image),
        buttonBackgroundImage(R.mipmap.button_wall_background_image);
    }

    public inline fun <reified T> wallBackgroundImage(type: ImageType): T = this.image(Images.wallBackgroundImage.value, type)
    public inline fun <reified T> userAvatarPlaceholderSmallImage(type: ImageType): T = this.image(Images.userAvatarPlaceholderSmallImage.value, type)
    public inline fun <reified T> likeSmallImage(type: ImageType): T = this.image(Images.likeSmallImage.value, type)
    public inline fun <reified T> dislikeSmallImage(type: ImageType): T = this.image(Images.dislikeSmallImage.value, type)
    public inline fun <reified T> backArrowSmallImage(type: ImageType): T = this.image(Images.backArrowSmallImage.value, type)
    public inline fun <reified T> neonLogoMediumImage(type: ImageType): T = this.image(Images.neonLogoMediumImage.value, type)
    public inline fun <reified T> answerSmallImage(type: ImageType): T = this.image(Images.answerSmallImage.value, type)
    public inline fun <reified T> buttonBackgroundImage(type: ImageType): T = this.image(Images.buttonBackgroundImage.value, type)

    public enum class ImageType(val value: Int) {
        drawable(0),
        bitmap(1),
        id(2)
    }
    // endregion

    // region Fonts
    public enum class Fonts(val value: Int) {
        regular(R.font.helvetica),
        bold(R.font.helvetica_bold),
        light(R.font.helvetica_light),
        oblique(R.font.helvetica_oblique),
        boldOblique(R.font.helvetica_bold_oblique);
    }

    public fun regular(): Typeface { return this.getFont(Fonts.regular.value) }
    public fun bold(): Typeface { return this.getFont(Fonts.bold.value) }
    public fun light(): Typeface { return this.getFont(Fonts.light.value) }
    public fun oblique(): Typeface { return this.getFont(Fonts.oblique.value) }
    public fun boldOblique(): Typeface { return this.getFont(Fonts.boldOblique.value) }
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