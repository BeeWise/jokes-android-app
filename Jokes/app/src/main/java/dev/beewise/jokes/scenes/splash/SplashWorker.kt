package dev.beewise.jokes.scenes.splash

import dev.beewise.jokes.models.user.User
import dev.beewise.jokes.operations.base.errors.OperationError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

interface SplashWorkerDelegate {
    fun successDidFetchUserDetails(user: User?)
    fun failureDidFetchUserDetails(error: OperationError)
}

open class SplashWorker(delegate: SplashWorkerDelegate?) {
    var delegate: WeakReference<SplashWorkerDelegate>? = null

    init {
        this.delegate = WeakReference<SplashWorkerDelegate>(delegate)
    }

    fun fetchUserDetails() {
        CoroutineScope(Dispatchers.Default).launch {
            delay((1000L..2000L).random())
            this@SplashWorker.delegate?.get()?.successDidFetchUserDetails(this@SplashWorker.user())
        }
    }

    private fun user(): User {
        return User(1, "uuid1", null, "username", "email@mail.com", "Name", null)
    }
}
