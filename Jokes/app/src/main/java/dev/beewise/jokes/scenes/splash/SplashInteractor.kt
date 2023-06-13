package dev.beewise.jokes.scenes.splash

interface SplashBusinessLogic {
    fun shouldDoSomething(request: SplashModels.Something.Request)
}

class SplashInteractor : SplashBusinessLogic, SplashWorkerDelegate {
    var presenter: SplashPresentationLogic? = null
    var worker: SplashWorker? = SplashWorker(this)

    override fun shouldDoSomething(request: SplashModels.Something.Request) {
        this.presenter?.presentSomething(SplashModels.Something.Response(request.value))
    }
}
