package dev.beewise.jokes.scenes.jokes

import java.lang.ref.WeakReference

interface JokesRoutingLogic {
    fun dismissActivity()
}

class JokesRouter : JokesRoutingLogic {
    var activity: WeakReference<JokesActivity>? = null

    override fun dismissActivity() {
        this.activity?.get()?.finish()
    }
}
