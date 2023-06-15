package dev.beewise.jokes.scenes.jokes

import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.models.joke.JokeOrderBy
import dev.beewise.jokes.operations.base.errors.OperationError

interface JokesBusinessLogic {
    fun shouldFetchJokes()

    fun shouldRefreshDetails()

    fun shouldSelectReadAnswer(request: JokesModels.ItemSelection.Request)

    fun shouldSelectLogo()
}

class JokesInteractor : JokesBusinessLogic, JokesWorkerDelegate {
    var presenter: JokesPresentationLogic? = null
    var worker: JokesWorker? = JokesWorker(this)

    var paginationModel: JokesModels.PaginationModel = JokesModels.PaginationModel()

    override fun shouldFetchJokes() {
        if (!this.paginationModel.isFetchingItems && !this.paginationModel.noMoreItems) {
            this.paginationModel.isFetchingItems = true
            this.presenter?.presentLoadingState()
            this.worker?.fetchJokes(this.paginationModel.currentPage, this.paginationModel.limit, JokeOrderBy.latest.value)
        }
    }

    override fun successDidFetchJokes(jokes: ArrayList<Joke>) {
        this.paginationModel.isFetchingItems = false
        jokes.forEach { this.paginationModel.items.add(it) }
        this.paginationModel.currentPage += 1
        this.paginationModel.hasError = false

        this.presenter?.presentNotLoadingState()
        this.presentItems()
        this.presenter?.presentRemoveError()

        this.shouldVerifyNoMoreItems(jokes.size)
    }

    override fun failureDidFetchJokes(error: OperationError) {
        this.paginationModel.isFetchingItems = false
        this.paginationModel.hasError = true
        this.presenter?.presentNotLoadingState()
        this.presenter?.presentError(JokesModels.ErrorPresentation.Response(error))
    }

    override fun shouldRefreshDetails() {
        this.paginationModel.reset()

        this.presentItems()
        this.presenter?.presentRemoveError()
        this.presenter?.presentRemoveNoMoreItems()

        this.shouldFetchJokes()
    }

    private fun presentItems() {
        this.presenter?.presentItems(JokesModels.ItemsPresentation.Response(this.itemsToPresent(), this.paginationModel.readJokes))
    }

    private fun itemsToPresent(): ArrayList<Joke> {
        return this.paginationModel.items
    }

    private fun shouldVerifyNoMoreItems(count: Int) {
        if (count < this.paginationModel.limit) {
            this.paginationModel.noMoreItems = true
            this.presenter?.presentNoMoreItems()
        }
    }

    fun joke(id: String?): Joke? {
        if (id == null) {
            return null
        }
        return this.paginationModel.items.find { it.uuid == id }
    }

    override fun shouldSelectReadAnswer(request: JokesModels.ItemSelection.Request) {
        val joke = this.joke(request.id)
        if (joke != null) {
            this.paginationModel.readJokes.add(joke)
            this.presenter?.presentReadState(JokesModels.ItemReadState.Response(true, joke.uuid))
        }
    }

    override fun shouldSelectLogo() {
        this.presenter?.presentScrollToItem(JokesModels.ItemScroll.Response(false, 0))
    }
}
