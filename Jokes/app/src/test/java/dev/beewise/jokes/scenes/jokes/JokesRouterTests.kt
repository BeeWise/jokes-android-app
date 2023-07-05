package dev.beewise.jokes.scenes.jokes

/*
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesActivitySpy
import dev.beewise.jokes.test_cases.RobolectricTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.ref.WeakReference

class JokesRouterTests : RobolectricTestCase() {
    lateinit var sut: JokesRouter
    lateinit var activitySpy: JokesActivitySpy

    // region Test lifecycle
    @Before
    public override fun setUp() {
        super.setUp()
        this.setupSubjectUnderTest()
    }

    @After
    public override fun tearDown() {
        super.tearDown()
    }
    // endregion

    private fun setupSubjectUnderTest() {
        this.sut = JokesRouter()

        this.activitySpy = JokesActivitySpy()
        this.sut.activity = WeakReference(this.activitySpy)
    }

    @Test
    fun testDismissActivity() {
        this.sut.dismissActivity()
        assertTrue(this.activitySpy.finishCalled)
    }
}
*/