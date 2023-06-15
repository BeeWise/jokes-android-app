package dev.beewise.jokes.scenes.jokes

import android.content.Context
import dev.beewise.jokes.R
import dev.beewise.jokes.managers.ContextManager

class JokesLocalization {
    companion object {
        val instance = JokesLocalization()
    }

    private fun context(): Context? = ContextManager.instance.context?.get()

    fun jokeOfTheDayTitle() = this.context()?.getString(R.string.Jokes_scene_joke_of_the_day_title) ?: ""

    fun jokeOfTheWeekTitle() = this.context()?.getString(R.string.Jokes_scene_joke_of_the_week_title) ?: ""

    fun jokeOfTheMonthTitle() = this.context()?.getString(R.string.Jokes_scene_joke_of_the_month_title) ?: ""

    fun jokeOfTheYearTitle() = this.context()?.getString(R.string.Jokes_scene_joke_of_the_year_title) ?: ""

    fun noMoreItemsText(): String = this.context()?.getString(R.string.Jokes_scene_no_more_items_text) ?: ""

    fun errorText() = this.context()?.getString(R.string.Jokes_scene_error_text) ?: ""

    fun sourceText(source: String) = this.context()?.getString(R.string.Jokes_scene_source_text, source) ?: ""
}
