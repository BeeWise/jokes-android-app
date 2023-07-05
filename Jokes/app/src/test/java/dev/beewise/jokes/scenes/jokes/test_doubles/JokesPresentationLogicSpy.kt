package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesModels
import dev.beewise.jokes.scenes.jokes.JokesPresentationLogic

class JokesPresentationLogicSpy : JokesPresentationLogic {
    var presentLoadingStateCalled: Boolean = false
    var presentNotLoadingStateCalled: Boolean = false

    var presentItemsCalled: Boolean = false

    var presentNoMoreItemsCalled: Boolean = false
    var presentRemoveNoMoreItemsCalled: Boolean = false

    var presentErrorCalled: Boolean = false
    var presentRemoveErrorCalled: Boolean = false

    var presentReadStateCalled: Boolean = false

    var presentScrollToItemCalled: Boolean = false

    var presentUserAvatarImageCalled: Boolean = false
    var presentUserAvatarImageLoadingStateCalled: Boolean = false

    override fun presentLoadingState() {
        this.presentLoadingStateCalled = true
    }

    override fun presentNotLoadingState() {
        this.presentNotLoadingStateCalled = true
    }

    override fun presentItems(response: JokesModels.ItemsPresentation.Response) {
        this.presentItemsCalled = true
    }

    override fun presentNoMoreItems() {
        this.presentNoMoreItemsCalled = true
    }

    override fun presentRemoveNoMoreItems() {
        this.presentRemoveNoMoreItemsCalled = true
    }

    override fun presentError(response: JokesModels.ErrorPresentation.Response) {
        this.presentErrorCalled = true
    }

    override fun presentRemoveError() {
        this.presentRemoveErrorCalled = true
    }

    override fun presentReadState(response: JokesModels.ItemReadState.Response) {
        this.presentReadStateCalled = true
    }

    override fun presentScrollToItem(response: JokesModels.ItemScroll.Response) {
        this.presentScrollToItemCalled = true
    }

    override fun presentUserAvatarImage(response: JokesModels.UserAvatarImage.Response) {
        this.presentUserAvatarImageCalled = true
    }

    override fun presentUserAvatarImageLoadingState(response: JokesModels.UserAvatarImageLoadingState.Response) {
        this.presentUserAvatarImageLoadingStateCalled = true
    }
}
