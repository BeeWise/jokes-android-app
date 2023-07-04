package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesAdapter
import dev.beewise.jokes.scenes.jokes.JokesAdapterDelegate

class JokesAdapterSpy(delegate: JokesAdapterDelegate) : JokesAdapter(delegate) {
    var setNewIsLoadingCalled: Boolean = false
    var setNewHasErrorCalled: Boolean = false
    var setNewNoMoreItemsCalled: Boolean = false
    var setNewItemsCalled: Boolean = false

    override fun setNewIsLoading(newIsLoading: Boolean) {
        this.setNewIsLoadingCalled = true
        super.setNewIsLoading(newIsLoading)
    }

    override fun setNewHasError(newHasError: Boolean) {
        this.setNewHasErrorCalled = true
        super.setNewHasError(newHasError)
    }

    override fun setNewNoMoreItems(newNoMoreItems: Boolean) {
        this.setNewNoMoreItemsCalled = true
        super.setNewNoMoreItems(newNoMoreItems)
    }

    override fun setNewItems(newItems: ArrayList<Any>) {
        this.setNewItemsCalled = true
        super.setNewItems(newItems)
    }
}