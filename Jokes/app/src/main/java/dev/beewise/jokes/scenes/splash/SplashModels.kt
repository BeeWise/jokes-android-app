package dev.beewise.jokes.scenes.splash

class SplashModels {
    enum class SceneType {
        splash,
        jokes
    }

    class SceneSetup {
        class Response(val type: SceneType)

        class ViewModel(val type: SceneType)
    }
}
