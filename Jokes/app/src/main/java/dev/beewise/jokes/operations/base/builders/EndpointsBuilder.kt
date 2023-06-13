import dev.beewise.jokes.BuildConfig

public final class EndpointsBuilder {
    companion object {
        val instance = EndpointsBuilder()
    }

    fun jokesEndpoint(): String {
        return BuildConfig.JOKES_ENDPOINT
    }
}