package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesBusinessLogic
import dev.beewise.jokes.scenes.jokes.JokesModels

class JokesBusinessLogicSpy : JokesBusinessLogic {
    var shouldFetchJokesCalled: Boolean = false
    var shouldRefreshDetailsCalled: Boolean = false
    var shouldSelectLogoCalled: Boolean = false
    var shouldSelectReadAnswerCalled: Boolean = false
    var shouldFetchUserAvatarImageCalled: Boolean = false

    override fun shouldFetchJokes() {
        this.shouldFetchJokesCalled = true
    }

    override fun shouldRefreshDetails() {
        this.shouldRefreshDetailsCalled = true
    }

    override fun shouldSelectLogo() {
        this.shouldSelectLogoCalled = true
    }

    override fun shouldSelectReadAnswer(request: JokesModels.ItemSelection.Request) {
        this.shouldSelectReadAnswerCalled = true
    }

    override fun shouldFetchUserAvatarImage(request: JokesModels.UserAvatarImage.Request) {
        this.shouldFetchUserAvatarImageCalled = true
    }
}
