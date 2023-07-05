package dev.beewise.jokes.tasks.spies

import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.operations.base.operations.Result
import dev.beewise.jokes.tasks.environment.TaskEnvironment
import dev.beewise.jokes.tasks.joke.JokeTask
import dev.beewise.jokes.tasks.joke.JokeTaskModels

class JokeTaskSpy: JokeTask(environment = TaskEnvironment.memory) {
    var fetchedJokes: ArrayList<Joke> = arrayListOf()
    var fetchJokesCalled: Boolean = false
    var shouldFailFetchJokes: Boolean = false

    override fun fetchJokes(model: JokeTaskModels.FetchJokes.Request, completionHandler: Result<JokeTaskModels.FetchJokes.Response>) {
        this.fetchJokesCalled = true

        if (this.shouldFailFetchJokes) {
            completionHandler.failure(OperationError.noDataAvailable)
        } else {
            completionHandler.success(JokeTaskModels.FetchJokes.Response(this.fetchedJokes))
        }
    }
}