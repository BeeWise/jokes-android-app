public class DispatchGroup {
    private var count = 0
    private var completion: (() -> Unit)? = null

    @Synchronized
    public fun enter() {
        this.count++
    }

    @Synchronized
    public fun leave() {
        this.count--
        this.notifyGroup()
    }

    public fun notify(completion: () -> Unit) {
        this.completion = completion
        this.notifyGroup()
    }

    private fun notifyGroup() {
        if (this.count <= 0 && this.completion != null) {
            this.completion?.invoke()
        }
    }
}