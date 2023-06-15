package dev.beewise.jokes.application

import android.content.Context
import dev.beewise.jokes.R
import dev.beewise.jokes.managers.ContextManager

class JokesApplicationLocalization {
    companion object {
        val instance = JokesApplicationLocalization()
    }

    private fun context(): Context? = ContextManager.instance.context?.get()

    fun cancelTitle() = this.context()?.getString(R.string.Cancel_title) ?: ""

    fun saveTitle() = this.context()?.getString(R.string.Save_title) ?: ""

    fun doneTitle() = this.context()?.getString(R.string.Done_title) ?: ""

    fun okTitle() = this.context()?.getString(R.string.OK_title) ?: ""

    fun jokesTitle(): String = this.context()?.getString(R.string.Jokes_title) ?: ""

    fun jokeStatusPendingTitle() = this.context()?.getString(R.string.Joke_status_pending_title) ?: ""

    fun jokeStatusApprovedTitle() = this.context()?.getString(R.string.Joke_status_approved_title) ?: ""

    fun jokeStatusRejectedTitle() = this.context()?.getString(R.string.Joke_status_rejected_title) ?: ""

    fun jokeStatusAdminRemovedTitle() = this.context()?.getString(R.string.Joke_status_admin_removed_title) ?: ""

    fun jokeStatusOwnerRemovedTitle() = this.context()?.getString(R.string.Joke_status_owner_removed_title) ?: ""

    fun usernameTitle(username: String) = this.context()?.getString(R.string.Username_title, username) ?: ""

    fun shareJokeTitle() = this.context()?.getString(R.string.Share_joke_title) ?: ""

    fun shareJokeMessage() = this.context()?.getString(R.string.Share_joke_message) ?: ""

    fun readAnswerTitle() = this.context()?.getString(R.string.Read_answer_title) ?: ""

    fun iOSTitle() = this.context()?.getString(R.string.iOS_title) ?: ""

    fun androidTitle() = this.context()?.getString(R.string.Android_title) ?: ""

    fun jokeTypeTextTitle() = this.context()?.getString(R.string.Joke_type_text_title) ?: ""

    fun jokeTypeQnaTitle() = this.context()?.getString(R.string.Joke_type_qna_title) ?: ""
}
