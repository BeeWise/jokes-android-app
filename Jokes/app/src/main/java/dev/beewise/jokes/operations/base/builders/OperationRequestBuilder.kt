package dev.beewise.jokes.operations.base.builders

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.http.HttpMethod
import org.json.JSONObject
import java.util.*

interface RequestBuilderInterface {
    fun url(): String
    fun request(): Request
    fun httpMethod(): HTTPMethod
    fun queryParameters(): HashMap<String, String?>
    fun bodyParameters(): HashMap<String, Any?>
    fun requiresAuthorization(): Boolean
}

enum class HTTPMethod(val value: String) {
    get("GET"),
    post("POST"),
    put("PUT"),
    delete("DELETE"),
    patch("PATCH")
}

open class OperationRequestBuilder<T>(var model: T) : RequestBuilderInterface {
    override fun url(): String {
        return ""
    }

    override fun request(): Request {
        val requestBuilder = Request.Builder()
        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("Accept", "application/json")
        requestBuilder.method(this.httpMethod().value, this.requestBody())
        return requestBuilder.url(this.urlBuilder().build()).build()
    }

    private fun urlBuilder(): HttpUrl.Builder {
        val urlBuilder = this.url().toHttpUrlOrNull()!!.newBuilder()

        if (this.queryParameters().size > 0) {
            this.queryParameters().forEach {
                urlBuilder.addQueryParameter(it.key, it.value)
            }
        }
        return urlBuilder
    }

    private fun requestBody(): RequestBody? {
        if (HttpMethod.requiresRequestBody(this.httpMethod().value) || HttpMethod.permitsRequestBody(this.httpMethod().value)) {
            return JSONObject(this.bodyParameters()).toString().toRequestBody("application/json".toMediaTypeOrNull())
        }
        return null
    }

    override fun httpMethod(): HTTPMethod {
        return HTTPMethod.get
    }

    override fun queryParameters(): HashMap<String, String?> {
        val parameters = hashMapOf<String, String?>()
        return parameters
    }

    override fun bodyParameters(): HashMap<String, Any?> {
        return hashMapOf()
    }

    override fun requiresAuthorization(): Boolean {
        return false
    }

    private enum class Parameters(val value: String) {
        
    }
}