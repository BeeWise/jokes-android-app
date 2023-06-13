package dev.beewise.jokes.tasks.configurator

import dev.beewise.jokes.tasks.environment.TaskEnvironment

public class TaskConfigurator {
    companion object {
        val instance = TaskConfigurator()
    }

    public var environment: TaskEnvironment = TaskEnvironment.memory
}