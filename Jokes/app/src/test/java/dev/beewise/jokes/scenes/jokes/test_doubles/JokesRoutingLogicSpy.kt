package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesRoutingLogic

class JokesRoutingLogicSpy : JokesRoutingLogic {
    var dismissActivityCalled: Boolean = false

    override fun dismissActivity() {
        this.dismissActivityCalled = true
    }
}
