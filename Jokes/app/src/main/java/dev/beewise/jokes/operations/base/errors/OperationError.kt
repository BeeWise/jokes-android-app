package dev.beewise.jokes.operations.base.errors

public sealed class OperationError {
    object noUrlAvailable : OperationError()
    object noDataAvailable : OperationError()
    object cannotParseResponse : OperationError()
    object noInternetConnection : OperationError()
    object operationCancelled : OperationError()
    object cannotRefreshToken : OperationError()
    data class error(val message: String, val code: Int?): OperationError()

    val errorMessage: String?
        get() {
            return when (this) {
                is noUrlAvailable -> "No url available."
                is noDataAvailable -> "No data available."
                is cannotParseResponse -> "Cannot parse response."
                is noInternetConnection -> "No internet connection."
                is operationCancelled -> ""
                is cannotRefreshToken -> "Cannot refresh token."
                is error -> this.message
                else -> return null
            }
        }
}