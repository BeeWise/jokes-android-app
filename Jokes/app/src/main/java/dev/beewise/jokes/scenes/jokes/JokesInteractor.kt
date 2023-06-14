package dev.beewise.jokes.scenes.jokes

interface JokesBusinessLogic {
    fun shouldDoSomething(request: JokesModels.Something.Request)
}

class JokesInteractor : JokesBusinessLogic, JokesWorkerDelegate {
    var presenter: JokesPresentationLogic? = null
    var worker: JokesWorker? = JokesWorker(this)

    override fun shouldDoSomething(request: JokesModels.Something.Request) {
        this.presenter?.presentSomething(JokesModels.Something.Response(request.value))
    }
}
