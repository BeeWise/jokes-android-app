package dev.beewise.jokes.scenes.splash

import java.lang.ref.WeakReference

interface SplashRoutingLogic {
    fun dismissActivity()
}

class SplashRouter : SplashRoutingLogic {
    var activity: WeakReference<SplashActivity>? = null

    override fun dismissActivity() {
        this.activity?.get()?.finish()
    }
}
