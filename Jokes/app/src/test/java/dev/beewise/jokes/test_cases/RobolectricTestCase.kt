package dev.beewise.jokes.test_cases

import android.os.Build
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@Config(sdk = [Build.VERSION_CODES.Q], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
open class RobolectricTestCase: TestCase() {
    fun waitForUIThread() {
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
    }

    @Test
    fun test() {

    }
}