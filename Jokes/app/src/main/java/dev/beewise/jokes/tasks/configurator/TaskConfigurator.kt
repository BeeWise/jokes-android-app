public class TaskConfigurator {
    companion object {
        val instance = TaskConfigurator()
    }

    public var environment: TaskEnvironment = TaskEnvironment.memory
}