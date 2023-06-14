package dev.beewise.jokes.scenes.splash

import android.graphics.drawable.Drawable
import dev.beewise.jokes.style.ApplicationStyle

public class SplashStyle {
    companion object {
        val instance = SplashStyle()
    }

    var contentViewModel: ContentViewModel = ContentViewModel()

    class ContentViewModel {
        fun backgroundImage(): Drawable { return ApplicationStyle.instance.wallBackgroundImage(ApplicationStyle.ImageType.drawable) }
    }
}
