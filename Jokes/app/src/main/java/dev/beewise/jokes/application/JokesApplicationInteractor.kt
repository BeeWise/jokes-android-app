package dev.beewise.jokes.application

import dev.beewise.jokes.BuildConfig
import dev.beewise.jokes.managers.ContextManager
import dev.beewise.jokes.tasks.configurator.TaskConfigurator
import dev.beewise.jokes.tasks.environment.TaskEnvironment
import java.lang.ref.WeakReference

interface  JokesApplicationBusinessLogic {
    fun shouldSetupApplicationContext(request: JokesApplicationModels.ContextSetup.Request)
    fun shouldSetupTaskConfiguratorEnvironment()
}

class JokesApplicationInteractor: JokesApplicationBusinessLogic {
    var taskEnvironment: TaskEnvironment? = null

    override fun shouldSetupApplicationContext(request: JokesApplicationModels.ContextSetup.Request) {
        ContextManager.instance.context = WeakReference(request.context)
    }

    override fun shouldSetupTaskConfiguratorEnvironment() {
        val environment = TaskEnvironment.from(BuildConfig.TASK_CONFIGURATOR_ENVIRONMENT)
        this.taskEnvironment = environment
        TaskConfigurator.instance.environment = environment
    }
}