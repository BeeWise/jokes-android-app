package dev.beewise.jokes.operations.jokes

import EndpointsBuilder
import HTTPMethod
import OperationRequestBuilder
import AsynchronousOperation
import OperationResult
import Result
import JSONFileOperation
import dev.beewise.jokes.managers.ModelManager
import dev.beewise.jokes.models.joke.Joke
import okhttp3.Request
import org.json.JSONObject
import java.util.HashMap

public class FetchJokesOperationModels {
    public class Request(val page: Int, val limit: Int, val orderBy: Int, val startedAt: String?, val endedAt: String?)

    public class Response {
        var jokes: ArrayList<Joke> = arrayListOf()
    }
}

class FetchJokesOperationRequestBuilder(model: FetchJokesOperationModels.Request): OperationRequestBuilder<FetchJokesOperationModels.Request>(model) {
    override fun url(): String {
        return EndpointsBuilder.instance.jokesEndpoint()
    }

    override fun httpMethod(): HTTPMethod {
        return HTTPMethod.get
    }

    override fun requiresAuthorization(): Boolean {
        return false
    }

    override fun queryParameters(): HashMap<String, String?> {
        val parameters: HashMap<String, String?> = hashMapOf()
        parameters[Parameters.page.value] = this.model.page.toString()
        parameters[Parameters.limit.value] = this.model.limit.toString()
        parameters[Parameters.orderBy.value] = this.model.orderBy.toString()
        if (this.model.startedAt != null) {
            parameters[Parameters.startedAt.value] = this.model.startedAt
        }
        if (this.model.endedAt != null) {
            parameters[Parameters.endedAt.value] = this.model.endedAt
        }
        return parameters
    }

    enum class Parameters(val value: String) {
        page("page"),
        limit("limit"),
        orderBy("order_by"),
        startedAt("started_at"),
        endedAt("ended_at");
    }
}

open class FetchJokesOperation(model: FetchJokesOperationModels.Request, completion: Result<OperationResult<FetchJokesOperationModels.Response>>?): AsynchronousOperation<FetchJokesOperationModels.Response>(model, completion) {
    override fun request(): Request {
        return FetchJokesOperationRequestBuilder(this.model as FetchJokesOperationModels.Request).request()
    }

    override fun parse(body: String?): FetchJokesOperationModels.Response? {
        if (body == null) return null
        val jsonObject = JSONObject(body)
        if (!jsonObject.has("data")) return null
        val jsonArray = jsonObject.optJSONArray("data")

        val response = FetchJokesOperationModels.Response()
        response.jokes = ModelManager.instance.parseJokes(jsonArray)
        return response
    }
}

open class FetchJokesLocalOperation(model: FetchJokesOperationModels.Request, completion: Result<OperationResult<FetchJokesOperationModels.Response>>?): JSONFileOperation<FetchJokesOperationModels.Response>(model, completion) {
    override fun fileName(): String? {
        return "json/fetch_jokes_local_operation.json"
    }

    override fun parseJson(json: String): FetchJokesOperationModels.Response? {
        val jsonObject = JSONObject(json)
        if (!jsonObject.has("data")) return null
        val jsonArray = jsonObject.optJSONArray("data")

        val response = FetchJokesOperationModels.Response()
        response.jokes = ModelManager.instance.parseJokes(jsonArray)
        return response
    }
}
