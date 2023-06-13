package dev.beewise.jokes.scenes.splash

import java.lang.ref.WeakReference

interface SplashPresentationLogic {
    fun presentSomething(response: SplashModels.Something.Response)
}

class SplashPresenter(displayLogic: SplashDisplayLogic) :
    SplashPresentationLogic {
    var displayer: WeakReference<SplashDisplayLogic>? = null

    init {
        this.displayer = WeakReference(displayLogic)
    }

    override fun presentSomething(response: SplashModels.Something.Response) {
        this.displayer?.get()?.displaySomething(SplashModels.Something.ViewModel(response.value))
    }
}
