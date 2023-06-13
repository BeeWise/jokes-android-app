import android.util.Log
import dev.beewise.jokes.BuildConfig
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

interface AsynchronousOperationInterface<T> {
    fun request(): Request
    
    fun parse(body: String?): T?
}

open class AsynchronousOperation<T>(var model: Any?, var completionHandler: Result<OperationResult<T>>?) : Operation(),
    AsynchronousOperationInterface<T> {
    var call: Call? = null

    var timeout: Long = 30
    var timeUnit: TimeUnit = TimeUnit.SECONDS

    var queue: OperationQueue = OperationQueue()

    init {
        this.setupCall()
    }

    open fun setupCall() {
        this.call = this.client().newCall(this.request())
    }

    private fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(this.timeout, this.timeUnit)
            .connectTimeout(this.timeout, this.timeUnit)
            .readTimeout(this.timeout, this.timeUnit)
            .writeTimeout(this.timeout, this.timeUnit)
            .build()
    }

    override fun request(): Request {
        return Request.Builder().build()
    }

    override fun parse(body: String?): T? {
        return null
    }

    override fun run(completion: (() -> Unit)?) {
        super.run(completion)
        if (this.shouldCancelOperation()) return

        this.call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@AsynchronousOperation.noDataAvailableErrorBlock()
            }

            override fun onResponse(call: Call, response: Response) {
                if (this@AsynchronousOperation.shouldCancelOperation()) {
                    response.body?.close()
                    return
                }

                val responseData = response.body?.string()
                this@AsynchronousOperation.log(response, responseData)

                when (OperationStatusCode.from(response.code)) {
                    OperationStatusCode.ok, OperationStatusCode.created, OperationStatusCode.accepted, OperationStatusCode.nonAuthoritativeInformation, OperationStatusCode.noContent, OperationStatusCode.resetContent, OperationStatusCode.partialContent -> this@AsynchronousOperation.verifyData(responseData)
                    OperationStatusCode.unprocessableEntity -> this@AsynchronousOperation.verifyError(responseData)
                    else -> this@AsynchronousOperation.noDataAvailableErrorBlock()
                }

                response.body?.close()
            }
        })
    }

    override fun cancel() {
        super.cancel()
        this.call?.cancel()
    }

    //region Verify & decode data
    fun verifyData(data: String?) {
        if (this.shouldCancelOperation()) {
            return
        }
        val finalData = data ?: return this.noDataAvailableErrorBlock()
        this.decodeData(finalData)
    }

    fun decodeData(data: String) {
        if (this.shouldCancelOperation()) {
            return
        }
        try {
            val value = this.parseData(data)
            this.transformData(value)
        } catch (error: Exception) {
            this.logError(error)
            this.cannotParseResponseErrorBlock()
        }
    }

    fun parseData(data: String): T? {
        return try {
            JSONObject(data)
            this.parse(data)
        } catch (jsonObjectError: Exception) {
            return try {
                JSONArray(data)
                this.parse(data)
            } catch (jsonArrayError: Exception) {
                this.parse(data)
            }
        }
    }

    fun transformData(response: T?) {
        if (this.shouldCancelOperation()) {
            return
        }
        val finalResponse = response ?: return this.noDataAvailableErrorBlock()
        this.successfulResultBlock(finalResponse)
    }

    fun successfulResultBlock(value: T) {
        this.completionHandler?.success(OperationResult(value))
        this.completion?.invoke()
    }
    //endregion

    //region Verify & decode error
    fun verifyError(data: String?) {
        if (this.shouldCancelOperation()) {
            return
        }
        val error = data ?: return this.noDataAvailableErrorBlock()
        this.decodeError(error)
    }

    private fun decodeError(data: String) {
        if (this.shouldCancelOperation()) {
            return
        }
        try {
            val value = this.parseError(data)
            this.transformError(value)
        } catch (error: Exception) {
            this.logError(error)
            this.cannotParseResponseErrorBlock()
        }
    }

    private fun transformError(error: ServerError) {
        val message = error.message
        if (message != null) {
            this.responseErrorBlock(message, null)
        } else {
            this.cannotParseResponseErrorBlock()
        }
    }

    private fun parseError(data: String): ServerError {
        try {
            val json = JSONObject(data)
            val message = if (json.has("message")) json.optString("message") else null
            val serverError = ServerError()
            serverError.message = message
            return serverError
        } catch (error: Exception) {
            throw error
        }
    }
    //endregion

    fun shouldCancelOperation(): Boolean {
        if (this.isCancelled) {
            this.cancelledOperationErrorBlock()
            return true
        }
        return false
    }

    //region Blocks
    fun responseErrorBlock(message: String, code: Int?) {
        this.completionHandler?.failure(OperationError.error(message = message, code = code))
        this.completion?.invoke()
    }

    fun noDataAvailableErrorBlock() {
        this.completionHandler?.failure(OperationError.noDataAvailable)
        this.completion?.invoke()
    }

    fun cannotParseResponseErrorBlock() {
        this.completionHandler?.failure(OperationError.cannotParseResponse)
        this.completion?.invoke()
    }

    fun cancelledOperationErrorBlock() {
        this.completionHandler?.failure(OperationError.operationCancelled)
        this.completion?.invoke()
    }

    fun cannotRefreshTokenErrorBlock() {
        this.completionHandler?.failure(OperationError.cannotRefreshToken)
        this.completion?.invoke()
    }
    //endregion

    //region Logging
    fun log(response: Response, responseData: String?) {
        if (BuildConfig.OPERATION_LOGGING_ENABLED.toBoolean()) {
            Log.i("AsynchronousOperation", "*************** START ${this::class.java.name} ***************")
            response.log(responseData)
            Log.i("AsynchronousOperation", "*************** END ${this::class.java.name} ***************")
        }
    }

    fun logError(error: Exception?) {
        if (BuildConfig.OPERATION_LOGGING_ENABLED.toBoolean()) {
            Log.i("AsynchronousOperation", "*************** START ${this::class.java.name} ***************")
            Log.i("Error", "${error?.localizedMessage}")
            Log.i("AsynchronousOperation", "*************** END ${this::class.java.name} ***************")
        }
    }
    //endregion

    class ServerError {
        var message: String? = null
    }
}

private fun Response.log(responseData: String?) {
    this.request.log()
    Log.i("Response", "Is successful: ${this.isSuccessful}")
    Log.i("Response", "Data: $responseData")
    Log.i("Response", "HTTP status code: ${this.code}")
    Log.i("Response", "HTTP status message: ${this.message}")
    Log.i("Response", "HTTP header fields: ${this.headers}")
}

private fun Request.log() {
    Log.i("Request", "HTTP url absolute string: ${this.url}")
    Log.i("Request", "HTTP method: ${this.method}")
    Log.i("Request", "HTTP body: ${this.body?.toString()}")
    Log.i("Request", "HTTP header fields: ${this.headers}")
}