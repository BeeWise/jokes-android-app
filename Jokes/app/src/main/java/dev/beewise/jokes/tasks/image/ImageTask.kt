package dev.beewise.jokes.tasks.image

import android.graphics.Bitmap
import dev.beewise.jokes.operations.image.FetchImageBitmapLocalOperation
import dev.beewise.jokes.operations.image.FetchImageBitmapOperation
import dev.beewise.jokes.operations.image.FetchImageBitmapOperationModels
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.operations.base.operations.*
import dev.beewise.jokes.tasks.environment.TaskEnvironment
import dev.beewise.jokes.tasks.task_protocol.TaskProtocol

public class ImageTaskModels {
    public class FetchImageBitmap {
        public class Request(val imageUrl: String, val isRounded: Boolean)

        public class Response(val bitmap: Bitmap)
    }
}

public interface ImageTaskProtocol: TaskProtocol {
    fun fetchImageBitmap(model: ImageTaskModels.FetchImageBitmap.Request, completionHandler: Result<ImageTaskModels.FetchImageBitmap.Response>)
}

open class ImageTask(override var environment: TaskEnvironment): ImageTaskProtocol {
    private var fetchImageBitmapOperationQueue = OperationQueue()
    
    override fun fetchImageBitmap(model: ImageTaskModels.FetchImageBitmap.Request, completionHandler: Result<ImageTaskModels.FetchImageBitmap.Response>) {
        val operationModel = FetchImageBitmapOperationModels.Request(model.imageUrl, model.isRounded)
        val operation = this.fetchImageBitmapOperation(operationModel, completionHandler)
        this.fetchImageBitmapOperationQueue.addOperation(operation)
    }

    private fun fetchImageBitmapOperation(model: FetchImageBitmapOperationModels.Request, completionHandler: Result<ImageTaskModels.FetchImageBitmap.Response>): Operation {
        val operationCompletionHandler: Result<OperationResult<FetchImageBitmapOperationModels.Response>> = object : Result<OperationResult<FetchImageBitmapOperationModels.Response>> {
            override fun success(value: OperationResult<FetchImageBitmapOperationModels.Response>) {
                completionHandler.success(ImageTaskModels.FetchImageBitmap.Response(value.value.bitmap))
            }

            override fun failure(operationError: OperationError) {
                completionHandler.failure(operationError)
            }
        }
        return when (this.environment) {
            TaskEnvironment.production -> FetchImageBitmapOperation(model, operationCompletionHandler)
            TaskEnvironment.development -> FetchImageBitmapOperation(model, operationCompletionHandler)
            TaskEnvironment.memory -> FetchImageBitmapLocalOperation(model, operationCompletionHandler)
        }
    }
}
