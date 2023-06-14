package dev.beewise.jokes.scenes.jokes

import java.lang.ref.WeakReference

interface JokesPresentationLogic {
    fun presentSomething(response: JokesModels.Something.Response)
}

class JokesPresenter(displayLogic: JokesDisplayLogic) :
    JokesPresentationLogic {
    var displayer: WeakReference<JokesDisplayLogic>? = null

    init {
        this.displayer = WeakReference(displayLogic)
    }

    override fun presentSomething(response: JokesModels.Something.Response) {
        this.displayer?.get()?.displaySomething(JokesModels.Something.ViewModel(response.value))
    }
}
