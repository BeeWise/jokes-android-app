package dev.beewise.jokes.scenes.jokes

/*
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesRoutingLogicSpy
import dev.beewise.jokes.test_cases.RobolectricTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
class JokesActivityTests : RobolectricTestCase() {
    lateinit var sut: JokesActivity
    lateinit var routerSpy: JokesRoutingLogicSpy

    @Before
    public override fun setUp() {
        super.setUp()
        this.setupSubjectUnderTest()
    }

    @After
    public override fun tearDown() {
        super.tearDown()
    }

    private fun setupSubjectUnderTest() {
        this.sut = JokesActivity()

        this.routerSpy = JokesRoutingLogicSpy()
        this.sut.router = this.routerSpy
    }

    @Test
    fun testJokesFragmentDismissSceneShouldAskTheRouterToDismissActivity() {
        this.sut.jokesFragmentDismissScene(JokesFragment())
        assertTrue(this.routerSpy.dismissActivityCalled)
    }
}
*/