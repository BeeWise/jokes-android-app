package dev.beewise.jokes.scenes.splash

import dev.beewise.jokes.scenes.jokes.JokesActivity
import java.lang.ref.WeakReference

interface SplashRoutingLogic {
    fun dismissActivity()
    fun navigateToJokes()
}

class SplashRouter : SplashRoutingLogic {
    var activity: WeakReference<SplashActivity>? = null

    override fun dismissActivity() {
        this.activity?.get()?.finish()
    }

    override fun navigateToJokes() {
        this.activity?.get()?.let {
             it.startActivity(JokesActivity.intent(it))
        }
    }
}
