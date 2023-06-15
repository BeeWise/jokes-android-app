package dev.beewise.jokes.tasks.configurator

import dev.beewise.jokes.tasks.environment.TaskEnvironment
import dev.beewise.jokes.tasks.joke.JokeTask
import dev.beewise.jokes.tasks.joke.JokeTaskProtocol

public class TaskConfigurator {
    companion object {
        val instance = TaskConfigurator()
    }

    public var environment: TaskEnvironment = TaskEnvironment.memory

    fun jokeTask(): JokeTaskProtocol {
        return JokeTask(this.environment)
    }
}