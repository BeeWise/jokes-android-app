package dev.beewise.jokes.scenes.splash

import java.lang.ref.WeakReference

interface SplashWorkerDelegate {
}

open class SplashWorker(delegate: SplashWorkerDelegate?) {
    var delegate: WeakReference<SplashWorkerDelegate>? = null

    init {
        this.delegate = WeakReference<SplashWorkerDelegate>(delegate)
    }
}
