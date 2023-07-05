package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesDisplayLogic
import dev.beewise.jokes.scenes.jokes.JokesModels

class JokesDisplayLogicSpy : JokesDisplayLogic {
    var displayLoadingStateCalled: Boolean = false
    var displayNotLoadingStateCalled: Boolean = false

    var displayItemsCalled: Boolean = false
    var displayNoMoreItemsCalled: Boolean = false
    var displayRemoveNoMoreItemsCalled: Boolean = false

    var displayErrorCalled: Boolean = false
    var displayRemoveErrorCalled: Boolean = false

    var displayReadStateCalled: Boolean = false
    var displayScrollToItemCalled: Boolean = false

    var displayUserAvatarImageCalled: Boolean = false
    var displayUserAvatarImageLoadingStateCalled: Boolean = false

    override fun displayLoadingState() {
        this.displayLoadingStateCalled = true
    }

    override fun displayNotLoadingState() {
        this.displayNotLoadingStateCalled = true
    }

    override fun displayItems(viewModel: JokesModels.ItemsPresentation.ViewModel) {
        this.displayItemsCalled = true
    }

    override fun displayNoMoreItems(viewModel: JokesModels.NoMoreItemsPresentation.ViewModel) {
        this.displayNoMoreItemsCalled = true
    }

    override fun displayRemoveNoMoreItems() {
        this.displayRemoveNoMoreItemsCalled = true
    }

    override fun displayError(viewModel: JokesModels.ErrorPresentation.ViewModel) {
        this.displayErrorCalled = true
    }

    override fun displayRemoveError() {
        this.displayRemoveErrorCalled = true
    }

    override fun displayReadState(viewModel: JokesModels.ItemReadState.ViewModel) {
        this.displayReadStateCalled = true
    }

    override fun displayScrollToItem(viewModel: JokesModels.ItemScroll.ViewModel) {
        this.displayScrollToItemCalled = true
    }

    override fun displayUserAvatarImage(viewModel: JokesModels.UserAvatarImage.ViewModel) {
        this.displayUserAvatarImageCalled = true
    }

    override fun displayUserAvatarImageLoadingState(viewModel: JokesModels.UserAvatarImageLoadingState.ViewModel) {
        this.displayUserAvatarImageLoadingStateCalled = true
    }
}
