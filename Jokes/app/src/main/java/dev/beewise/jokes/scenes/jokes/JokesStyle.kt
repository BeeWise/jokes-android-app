package dev.beewise.jokes.scenes.jokes

import dev.beewise.jokes.style.ApplicationStyle

public class JokesStyle {
    companion object {
        val instance = JokesStyle()
    }

    var contentViewModel: ContentViewModel = ContentViewModel()

    class ContentViewModel {
        fun backgroundColor(): Int { return ApplicationStyle.instance.backgroundColor() }
    }
}
