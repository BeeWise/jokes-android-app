package dev.beewise.jokes.scenes.splash

import dev.beewise.jokes.style.ApplicationStyle

public class SplashStyle {
    companion object {
        val instance = SplashStyle()
    }

    var contentViewModel: ContentViewModel = ContentViewModel()

    class ContentViewModel {
        fun backgroundColor(): Int { return ApplicationStyle.instance.backgroundColor() }
    }
}
