package dev.beewise.jokes.scenes.jokes

import dev.beewise.jokes.scenes.jokes.test_doubles.JokesWorkerDelegateSpy
import dev.beewise.jokes.tasks.spies.ImageTaskSpy
import dev.beewise.jokes.tasks.spies.JokeTaskSpy
import dev.beewise.jokes.test_cases.RobolectricTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class JokesWorkerTests : RobolectricTestCase() {
    lateinit var sut: JokesWorker
    lateinit var delegateSpy: JokesWorkerDelegateSpy

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
        this.delegateSpy = JokesWorkerDelegateSpy()
        this.sut = JokesWorker(this.delegateSpy)
    }

    @Test
    fun testFetchJokesShouldAskTheJokeTaskToFetchJokes() {
        val taskSpy = JokeTaskSpy()
        this.sut.jokeTask = taskSpy
        this.sut.fetchJokes(1, 10, 0)
        assertTrue(taskSpy.fetchJokesCalled)
    }

    @Test
    fun testFetchJokesShouldAskTheDelegateToSendJokesForSuccessCase() {
        val taskSpy = JokeTaskSpy()
        taskSpy.shouldFailFetchJokes = false
        this.sut.jokeTask = taskSpy
        this.sut.fetchJokes(1, 10, 0)
        assertTrue(this.delegateSpy.successDidFetchJokesCalled)
    }

    @Test
    fun testFetchJokesShouldAskTheDelegateToSendErrorForFailureCase() {
        val taskSpy = JokeTaskSpy()
        taskSpy.shouldFailFetchJokes = true
        this.sut.jokeTask = taskSpy
        this.sut.fetchJokes(1, 10, 0)
        assertTrue(this.delegateSpy.failureDidFetchJokesCalled)
    }

    @Test
    fun testFetchUserAvatarImageShouldAskTheJokeTaskToFetchImageBitmap() {
        val taskSpy = ImageTaskSpy()
        this.sut.imageTask = taskSpy
        this.sut.fetchUserAvatarImage("uuid", "imageUrl", false)
        assertTrue(taskSpy.fetchImageBitmapCalled)
    }

    @Test
    fun testFetchUserAvatarImageShouldAskTheDelegateToSendImageBitmapForSuccessCase() {
        val taskSpy = ImageTaskSpy()
        taskSpy.shouldFailFetchImageBitmap = false
        this.sut.imageTask = taskSpy
        this.sut.fetchUserAvatarImage("uuid", "imageUrl", false)
        assertTrue(this.delegateSpy.successDidFetchUserAvatarImageCalled)
    }

    @Test
    fun testFetchUserAvatarImageShouldAskTheDelegateToSendErrorForFailureCase() {
        val taskSpy = ImageTaskSpy()
        taskSpy.shouldFailFetchImageBitmap = true
        this.sut.imageTask = taskSpy
        this.sut.fetchUserAvatarImage("uuid", "imageUrl", false)
        assertTrue(this.delegateSpy.failureDidFetchUserAvatarImageCalled)
    }
}
