package dev.beewise.jokes.operations.base.operations

import dev.beewise.jokes.operations.base.errors.OperationError
import java.util.*

open class Operation {
    val id: String = UUID.randomUUID().toString()

    protected var isLoggingEnabled: Boolean = false
    var isCancelled: Boolean = false
    var completion: (() -> Unit)? = null

    open fun run(completion: (() -> Unit)?) {
        this.completion = completion
    }

    open fun cancel() {
        this.isCancelled = true
    }

    open fun log() {
        if (!this.isLoggingEnabled) return
    }

    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != this.javaClass) return false

        other as Operation
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

interface Result<T> {
    fun success(value: T)
    fun failure(operationError: OperationError)
}