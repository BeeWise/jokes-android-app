package dev.beewise.jokes.scenes.splash

import java.lang.ref.WeakReference

interface SplashPresentationLogic {
    fun presentSetupScene(response: SplashModels.SceneSetup.Response)
    fun presentDismissScene()
}

class SplashPresenter(displayLogic: SplashDisplayLogic) :
    SplashPresentationLogic {
    var displayer: WeakReference<SplashDisplayLogic>? = null

    init {
        this.displayer = WeakReference(displayLogic)
    }

    override fun presentSetupScene(response: SplashModels.SceneSetup.Response) {
        this.displayer?.get()?.displaySetupScene(SplashModels.SceneSetup.ViewModel(response.type))
    }

    override fun presentDismissScene() {
        this.displayer?.get()?.displayDismissScene()
    }
}
