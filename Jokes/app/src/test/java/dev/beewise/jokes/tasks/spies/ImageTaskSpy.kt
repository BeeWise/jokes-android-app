package dev.beewise.jokes.tasks.spies

import android.graphics.Bitmap
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.operations.base.operations.Result
import dev.beewise.jokes.tasks.environment.TaskEnvironment
import dev.beewise.jokes.tasks.image.ImageTask
import dev.beewise.jokes.tasks.image.ImageTaskModels

class ImageTaskSpy: ImageTask(environment = TaskEnvironment.memory) {
    var fetchImageBitmapCalled: Boolean = false
    var shouldFailFetchImageBitmap: Boolean = false
    var fetchedBitmap: Bitmap = this.emptyBitmap()

    override fun fetchImageBitmap(model: ImageTaskModels.FetchImageBitmap.Request, completionHandler: Result<ImageTaskModels.FetchImageBitmap.Response>) {
        this.fetchImageBitmapCalled = true

        if (this.shouldFailFetchImageBitmap) {
            completionHandler.failure(OperationError.noDataAvailable)
        } else {
            completionHandler.success(ImageTaskModels.FetchImageBitmap.Response(this.fetchedBitmap))
        }
    }

    private fun emptyBitmap(): Bitmap {
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}