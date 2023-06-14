package dev.beewise.jokes.application

import android.app.Application

class JokesApplication: Application() {
    companion object {
        lateinit var instance: JokesApplication
    }

    var interactor: JokesApplicationBusinessLogic? = null

    init {
        instance = this
        this.setup()
    }

    private fun setup() {
        this.interactor = JokesApplicationInteractor()
    }

    override fun onCreate() {
        super.onCreate()

        this.interactor?.shouldSetupApplicationContext(JokesApplicationModels.ContextSetup.Request(this.applicationContext))
        this.interactor?.shouldSetupTaskConfiguratorEnvironment()
    }
}