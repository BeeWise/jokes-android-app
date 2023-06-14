package dev.beewise.jokes.scenes.splash

import dev.beewise.jokes.models.user.User
import dev.beewise.jokes.operations.base.errors.OperationError

interface SplashBusinessLogic {
    fun shouldFetchUserDetails()
}

class SplashInteractor : SplashBusinessLogic, SplashWorkerDelegate {
    var presenter: SplashPresentationLogic? = null
    var worker: SplashWorker? = SplashWorker(this)

    var isFetchingUserDetails: Boolean = false

    override fun shouldFetchUserDetails() {
        if (!this.isFetchingUserDetails) {
            this.isFetchingUserDetails = true
            this.worker?.fetchUserDetails()
        }
    }

    override fun successDidFetchUserDetails(user: User?) {
        User.currentUser = user

        this.isFetchingUserDetails = false
        this.presenter?.presentSetupScene(SplashModels.SceneSetup.Response(SplashModels.SceneType.jokes))
        this.presenter?.presentDismissScene()
    }

    override fun failureDidFetchUserDetails(error: OperationError) {
        this.isFetchingUserDetails = false
    }
}
