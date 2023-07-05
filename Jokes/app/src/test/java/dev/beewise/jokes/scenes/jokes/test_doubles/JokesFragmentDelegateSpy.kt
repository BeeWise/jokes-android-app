package dev.beewise.jokes.scenes.jokes.test_doubles

import dev.beewise.jokes.scenes.jokes.JokesFragment
import dev.beewise.jokes.scenes.jokes.JokesFragmentDelegate

class JokesFragmentDelegateSpy : JokesFragmentDelegate {
    var jokesFragmentDismissSceneCalled: Boolean = false

    override fun jokesFragmentDismissScene(fragment: JokesFragment) {
        this.jokesFragmentDismissSceneCalled = true
    }
}
