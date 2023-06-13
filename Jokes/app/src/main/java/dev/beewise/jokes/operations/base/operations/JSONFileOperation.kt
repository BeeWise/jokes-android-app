package dev.beewise.jokes.operations.base.operations

import android.content.res.AssetManager
import dev.beewise.jokes.managers.ContextManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class JSONFileOperation<T>(model: Any? = null, completion: Result<OperationResult<T>>?): AsynchronousOperation<T>(model, completion) {
    override fun setupCall() {
        this.call = null
    }

    open fun fileName(): String? {
        return null
    }

    open fun parseJson(json: String): T? {
        return null
    }

    override fun run(completion: (() -> Unit)?) {
        CoroutineScope(Dispatchers.Default).launch {
            ContextManager.instance.context?.get()?.let { context ->
                this@JSONFileOperation.fileName()?.let { fileName ->
                    val json = context.assets.readFile(fileName)
                    this@JSONFileOperation.parseJson(json)?.let { value ->
                        this@JSONFileOperation.successfulResultBlock(value)
                    } ?: run {
                        this@JSONFileOperation.noDataAvailableErrorBlock()
                    }
                } ?: run {
                    this@JSONFileOperation.noDataAvailableErrorBlock()
                }
            }?: run {
                this@JSONFileOperation.noDataAvailableErrorBlock()
            }
        }
    }
}

private fun AssetManager.readFile(fileName: String) = open(fileName)
    .bufferedReader()
    .use { it.readText() }