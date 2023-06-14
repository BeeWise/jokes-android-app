package dev.beewise.jokes.scenes.jokes

import java.lang.ref.WeakReference

interface JokesWorkerDelegate {
}

open class JokesWorker(delegate: JokesWorkerDelegate?) {
    var delegate: WeakReference<JokesWorkerDelegate>? = null

    init {
        this.delegate = WeakReference<JokesWorkerDelegate>(delegate)
    }
}
