package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesWorker
import dev.beewise.jokes.scenes.jokes.JokesWorkerDelegate

class JokesWorkerSpy(delegate: JokesWorkerDelegate) : JokesWorker(delegate) {
    var fetchJokesCalled: Boolean = false
    var fetchUserAvatarImageCalled: Boolean = false

    override fun fetchJokes(page: Int, limit: Int, orderBy: Int) {
        this.fetchJokesCalled = true
    }

    override fun fetchUserAvatarImage(uuid: String?, imageUrl: String, isRounded: Boolean) {
        this.fetchUserAvatarImageCalled = true
    }
}
