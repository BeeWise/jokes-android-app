public enum class TaskEnvironment(val value: String?) {
    production("production"),
    development("development"),
    memory("memory");

    companion object {
        fun from(value: String?): TaskEnvironment {
            return values().firstOrNull { it.value == value } ?: memory
        }
    }
}