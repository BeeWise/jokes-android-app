package dev.beewise.jokes.scenes.jokes

import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.tasks.configurator.TaskConfigurator
import dev.beewise.jokes.tasks.joke.JokeTaskModels
import dev.beewise.jokes.tasks.joke.JokeTaskProtocol
import dev.beewise.jokes.operations.base.operations.Result
import java.lang.ref.WeakReference

interface JokesWorkerDelegate {
    fun successDidFetchJokes(jokes: ArrayList<Joke>)
    fun failureDidFetchJokes(error: OperationError)
}

open class JokesWorker(delegate: JokesWorkerDelegate?) {
    var delegate: WeakReference<JokesWorkerDelegate>? = null

    var jokeTask: JokeTaskProtocol = TaskConfigurator.instance.jokeTask()

    init {
        this.delegate = WeakReference<JokesWorkerDelegate>(delegate)
    }

    fun fetchJokes(page: Int, limit: Int, orderBy: Int) {
        this.jokeTask.fetchJokes(JokeTaskModels.FetchJokes.Request(page, limit, orderBy, null, null), object : Result<JokeTaskModels.FetchJokes.Response> {
            override fun success(value: JokeTaskModels.FetchJokes.Response) {
                this@JokesWorker.delegate?.get()?.successDidFetchJokes(value.jokes)
            }

            override fun failure(operationError: OperationError) {
                this@JokesWorker.delegate?.get()?.failureDidFetchJokes(operationError)
            }
        })
    }
}
