package dev.beewise.jokes.tasks.joke

import dev.beewise.jokes.operations.jokes.FetchJokesLocalOperation
import dev.beewise.jokes.operations.jokes.FetchJokesOperation
import dev.beewise.jokes.operations.jokes.FetchJokesOperationModels
import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.operations.base.operations.AsynchronousOperation
import dev.beewise.jokes.operations.base.operations.OperationQueue
import dev.beewise.jokes.operations.base.operations.OperationResult
import dev.beewise.jokes.tasks.environment.TaskEnvironment
import dev.beewise.jokes.operations.base.operations.Result
import dev.beewise.jokes.tasks.task_protocol.TaskProtocol

public class JokeTaskModels {
    public class FetchJokes {
        public class Request(val page: Int, val limit: Int, val orderBy: Int, val startedAt: String?, val endedAt: String?)

        public class Response(val jokes: ArrayList<Joke>)
    }
}

public interface JokeTaskProtocol: TaskProtocol {
    fun fetchJokes(model: JokeTaskModels.FetchJokes.Request, completionHandler: Result<JokeTaskModels.FetchJokes.Response>)
}

class JokeTask(override var environment: TaskEnvironment): JokeTaskProtocol {
    public var fetchJokesOperationQueue = OperationQueue()
    
    override fun fetchJokes(model: JokeTaskModels.FetchJokes.Request, completionHandler: Result<JokeTaskModels.FetchJokes.Response>) {
        val operationModel = FetchJokesOperationModels.Request(model.page, model.limit, model.orderBy, model.startedAt, model.endedAt)
        val operation = this.fetchJokesOperation(operationModel, completionHandler)
        this.fetchJokesOperationQueue.addOperation(operation)
    }

    private fun fetchJokesOperation(model: FetchJokesOperationModels.Request, completionHandler: Result<JokeTaskModels.FetchJokes.Response>): AsynchronousOperation<FetchJokesOperationModels.Response> {
        val operationCompletionHandler: Result<OperationResult<FetchJokesOperationModels.Response>> = object : Result<OperationResult<FetchJokesOperationModels.Response>> {
            override fun success(value: OperationResult<FetchJokesOperationModels.Response>) {
                completionHandler.success(JokeTaskModels.FetchJokes.Response(value.value.jokes))
            }

            override fun failure(operationError: OperationError) {
                completionHandler.failure(operationError)
            }
        }
        return when (this.environment) {
            TaskEnvironment.production -> FetchJokesOperation(model, operationCompletionHandler)
            TaskEnvironment.development -> FetchJokesOperation(model, operationCompletionHandler)
            TaskEnvironment.memory -> FetchJokesLocalOperation(model, operationCompletionHandler)
        }
    }
}
