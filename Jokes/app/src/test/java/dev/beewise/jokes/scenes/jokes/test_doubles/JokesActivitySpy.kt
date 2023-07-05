package dev.beewise.jokes.scenes.jokes.test_doubles

import android.content.Intent
import dev.beewise.jokes.scenes.jokes.JokesActivity

class JokesActivitySpy : JokesActivity() {
    var finishCalled: Boolean = false
    var startActivityCalled: Boolean = false

    override fun finish() {
        this.finishCalled = true
    }

    override fun startActivity(intent: Intent?) {
        this.startActivityCalled = true
    }
}
