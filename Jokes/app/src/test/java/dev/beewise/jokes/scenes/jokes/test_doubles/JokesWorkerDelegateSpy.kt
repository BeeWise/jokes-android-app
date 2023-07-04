package dev.beewise.jokes.scenes.jokes.test_doubles

import android.graphics.Bitmap
import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.scenes.jokes.JokesWorkerDelegate

class JokesWorkerDelegateSpy : JokesWorkerDelegate {
    var successDidFetchJokesCalled: Boolean = false
    var failureDidFetchJokesCalled: Boolean = false

    var successDidFetchUserAvatarImageCalled: Boolean = false
    var failureDidFetchUserAvatarImageCalled: Boolean = false

    override fun successDidFetchJokes(jokes: ArrayList<Joke>) {
        this.successDidFetchJokesCalled = true
    }

    override fun failureDidFetchJokes(error: OperationError) {
        this.failureDidFetchJokesCalled = true
    }

    override fun successDidFetchUserAvatarImage(uuid: String?, image: Bitmap?) {
        this.successDidFetchUserAvatarImageCalled = true
    }

    override fun failureDidFetchUserAvatarImage(uuid: String?, error: OperationError) {
        this.failureDidFetchUserAvatarImageCalled = true
    }
}
