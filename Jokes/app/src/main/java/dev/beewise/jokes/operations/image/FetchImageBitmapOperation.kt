package dev.beewise.jokes.operations.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.operations.base.operations.Operation
import dev.beewise.jokes.operations.base.operations.Result
import android.os.Handler
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dev.beewise.jokes.R
import dev.beewise.jokes.managers.ContextManager
import dev.beewise.jokes.operations.base.operations.OperationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@GlideModule
class JokesGlideModule: AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }
}

class FetchImageBitmapOperationModels {
    class Request(val imageUrl: String?, val isRounded: Boolean)

    class Response(val bitmap: Bitmap)
}

open class FetchImageBitmapOperation(val model: FetchImageBitmapOperationModels.Request, var completionHandler: Result<OperationResult<FetchImageBitmapOperationModels.Response>>?) : Operation() {
    var requestBuilder: RequestBuilder<Bitmap>? = null
    var customTarget: CustomTarget<Bitmap>? = null

    var timeout: Int = 30000

    init {
        this.setupRequestBuilder()
    }

    override fun run(completion: (() -> Unit)?) {
        super.run(completion)

        if (this.model.isRounded) {
            this.loadRoundedImage()
        } else {
            this.loadImage()
        }
    }

    private fun loadImage() {
        this.customTarget = this.requestBuilder?.load(this.model.imageUrl)?.into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                if (this@FetchImageBitmapOperation.shouldCancelOperation()) {
                    this@FetchImageBitmapOperation.clearResources()
                    return
                }
                this@FetchImageBitmapOperation.successfulResultBlock(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                this@FetchImageBitmapOperation.noDataAvailableErrorBlock()
                this@FetchImageBitmapOperation.clearResources()
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                this@FetchImageBitmapOperation.noDataAvailableErrorBlock()
                this@FetchImageBitmapOperation.clearResources()
            }
        })
    }

    private fun loadRoundedImage() {
        this.customTarget = this.requestBuilder?.load(this.model.imageUrl)?.circleCrop()?.into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                if (this@FetchImageBitmapOperation.shouldCancelOperation()) {
                    this@FetchImageBitmapOperation.clearResources()
                    return
                }
                this@FetchImageBitmapOperation.successfulResultBlock(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                this@FetchImageBitmapOperation.noDataAvailableErrorBlock()
                this@FetchImageBitmapOperation.clearResources()
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                this@FetchImageBitmapOperation.noDataAvailableErrorBlock()
                this@FetchImageBitmapOperation.clearResources()
            }
        })
    }

    override fun cancel() {
        super.cancel()
        this.clearResources()
    }

    private fun shouldCancelOperation(): Boolean {
        if (this.isCancelled) {
            this.cancelledOperationErrorBlock()
            return true
        }
        return false
    }

    private fun clearResources() {
        ContextManager.instance.context?.get()?.let {
            Handler(it.mainLooper).post {
                Glide.with(it).clear(this.customTarget)
            }
        }
    }

    //region Completion blocks
    private fun successfulResultBlock(bitmap: Bitmap) {
        this.completionHandler?.success(OperationResult(FetchImageBitmapOperationModels.Response(bitmap)))
        this.completion?.invoke()
    }

    private fun noDataAvailableErrorBlock() {
        this.completionHandler?.failure(OperationError.noDataAvailable)
        this.completion?.invoke()
    }

    private fun cancelledOperationErrorBlock() {
        this.completionHandler?.failure(OperationError.operationCancelled)
        this.completion?.invoke()
    }
    //endregion

    //region Request builder setup
    private fun setupRequestBuilder() {
        ContextManager.instance.context?.get()?.let {
            this.requestBuilder = Glide.with(it).asBitmap().timeout(this.timeout)
        } ?: this.noDataAvailableErrorBlock()
    }
    //endregion
}

open class FetchImageBitmapLocalOperation(val model: FetchImageBitmapOperationModels.Request, var completionHandler: Result<OperationResult<FetchImageBitmapOperationModels.Response>>?) : Operation() {
    var shouldFail: Boolean = false
    var delay: Long = (1000L..3000L).random()

    override fun run(completion: (() -> Unit)?) {
        if (this.shouldCancelOperation()) return

        ContextManager.instance.context?.get()?.let {
            CoroutineScope(Dispatchers.Default).launch {
                delay(delay)
                if (shouldCancelOperation()) return@launch

                if (shouldFail) {
                    noDataAvailableErrorBlock()
                } else {
                    val bitmap = BitmapFactory.decodeResource(it.resources, R.mipmap.ic_launcher)
                    if (bitmap != null) {
                        this@FetchImageBitmapLocalOperation.successfulResultBlock(bitmap)
                    } else {
                        this@FetchImageBitmapLocalOperation.noDataAvailableErrorBlock()
                    }
                }
            }
        } ?: this.noDataAvailableErrorBlock()
    }

    fun shouldCancelOperation(): Boolean {
        if (this.isCancelled) {
            this.cancelledOperationErrorBlock()
            return true
        }
        return false
    }

    fun successfulResultBlock(bitmap: Bitmap) {
        this.completionHandler?.success(OperationResult(FetchImageBitmapOperationModels.Response(bitmap)))
        this.completion?.invoke()
    }

    fun noDataAvailableErrorBlock() {
        this.completionHandler?.failure(OperationError.noDataAvailable)
        this.completion?.invoke()
    }

    fun cancelledOperationErrorBlock() {
        this.completionHandler?.failure(OperationError.operationCancelled)
        this.completion?.invoke()
    }
}