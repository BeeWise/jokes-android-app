package dev.beewise.jokes.scenes.jokes

import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.models.joke.JokeStatus
import dev.beewise.jokes.models.joke.JokeType
import dev.beewise.jokes.models.photo.Photo
import dev.beewise.jokes.models.user.User
import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesPresentationLogicSpy
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesWorkerSpy
import dev.beewise.jokes.test_cases.RobolectricTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class JokesInteractorTests : RobolectricTestCase() {
    lateinit var sut: JokesInteractor
    lateinit var presenterSpy: JokesPresentationLogicSpy
    lateinit var workerSpy: JokesWorkerSpy

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
        this.sut = JokesInteractor()

        this.presenterSpy = JokesPresentationLogicSpy()
        this.sut.presenter = this.presenterSpy

        this.workerSpy = JokesWorkerSpy(this.sut)
        this.sut.worker = this.workerSpy
    }
    
    @Test
    fun testShouldFetchJokesShouldSetIsFetchingItemsToTrueForPaginationModel() {
        this.sut.paginationModel.isFetchingItems = false
        this.sut.paginationModel.noMoreItems = false
        this.sut.shouldFetchJokes()
        assertTrue(this.sut.paginationModel.isFetchingItems)
    }

    @Test
    fun testShouldFetchJokesShouldAskThePresenterToPresentLoadingStateWhenItIsNotFetchingItemsAndThereAreMoreItems() {
        this.sut.paginationModel.isFetchingItems = false
        this.sut.paginationModel.noMoreItems = false
        this.sut.shouldFetchJokes()
        assertTrue(this.presenterSpy.presentLoadingStateCalled)
    }

    @Test
    fun testShouldFetchJokesShouldAskTheWorkerToFetchJokesWhenItIsNotFetchingItemsAndThereAreMoreItems() {
        this.sut.paginationModel.isFetchingItems = false
        this.sut.paginationModel.noMoreItems = false
        this.sut.shouldFetchJokes()
        assertTrue(this.workerSpy.fetchJokesCalled)
    }

    @Test
    fun testSuccessDidFetchJokesShouldSetIsFetchingItemsToFalseForPaginationModel() {
        this.sut.paginationModel.isFetchingItems = true
        this.sut.successDidFetchJokes(arrayListOf())
        assertFalse(this.sut.paginationModel.isFetchingItems)
    }

    @Test
    fun testSuccessDidFetchJokesShouldUpdateJokesForPaginationModel() {
        val items = arrayListOf(Joke(), Joke(), Joke())
        val itemsLength = items.size
        this.sut.paginationModel.items = items

        val jokes = arrayListOf(Joke(), Joke(), Joke())
        val jokesLength = jokes.size
        this.sut.successDidFetchJokes(jokes)
        assertEquals(this.sut.paginationModel.items.size, itemsLength + jokesLength)
    }

    @Test
    fun testSuccessDidFetchJokesShouldIncrementCurrentPageForPaginationModel() {
        val currentPage = 0
        this.sut.paginationModel.currentPage = currentPage
        this.sut.successDidFetchJokes(arrayListOf())
        assertEquals(this.sut.paginationModel.currentPage, currentPage + 1)
    }

    @Test
    fun testSuccessDidFetchJokesShouldSetHasErrorToFalseForPaginationModel() {
        this.sut.paginationModel.hasError = true
        this.sut.successDidFetchJokes(arrayListOf())
        assertFalse(this.sut.paginationModel.hasError)
    }

    @Test
    fun testSuccessDidFetchJokesShouldAskThePresenterToPresentNotLoadingState() {
        this.sut.successDidFetchJokes(arrayListOf())
        assertTrue(this.presenterSpy.presentNotLoadingStateCalled)
    }

    @Test
    fun testSuccessDidFetchJokesShouldAskThePresenterToPresentItems() {
        this.sut.successDidFetchJokes(arrayListOf())
        assertTrue(this.presenterSpy.presentItemsCalled)
    }

    @Test
    fun testSuccessDidFetchJokesShouldAskThePresenterToPresentRemoveError() {
        this.sut.successDidFetchJokes(arrayListOf())
        assertTrue(this.presenterSpy.presentRemoveErrorCalled)
    }

    @Test
    fun testSuccessDidFetchJokesShouldSetNoMoreItemsToTrueForPaginationModelWhenLimitReached() {
        this.sut.paginationModel.limit = 10
        this.sut.paginationModel.noMoreItems = false
        this.sut.successDidFetchJokes(arrayListOf())
        assertTrue(this.sut.paginationModel.noMoreItems)
    }

    @Test
    fun testSuccessDidFetchJokesShouldAskThePresenterToPresentNoMoreItemsWhenLimitReached() {
        this.sut.paginationModel.limit = 10
        this.sut.successDidFetchJokes(arrayListOf())
        assertTrue(this.presenterSpy.presentNoMoreItemsCalled)
    }

    @Test
    fun testShouldRefreshDetailsShouldResetPaginationModel() {
        this.sut.paginationModel.isFetchingItems = true
        this.sut.paginationModel.noMoreItems = true
        this.sut.paginationModel.hasError = true
        this.sut.paginationModel.currentPage = 10
        this.sut.paginationModel.limit = 100
        this.sut.paginationModel.items = arrayListOf(Joke(), Joke(), Joke())
        this.sut.paginationModel.readJokes = arrayListOf(Joke(), Joke())

        this.sut.shouldRefreshDetails()
        assertFalse(this.sut.paginationModel.noMoreItems)
        assertFalse(this.sut.paginationModel.hasError)
        assertEquals(this.sut.paginationModel.currentPage, 0)
        assertEquals(this.sut.paginationModel.limit, 10)
        assertEquals(this.sut.paginationModel.items.size, 0)
        assertEquals(this.sut.paginationModel.readJokes.size, 0)
    }

    @Test
    fun testShouldRefreshDetailsShouldAskThePresenterToPresentItems() {
        this.sut.shouldRefreshDetails()
        assertTrue(this.presenterSpy.presentItemsCalled)
    }

    @Test
    fun testShouldRefreshDetailsShouldAskThePresenterToPresentRemoveError() {
        this.sut.shouldRefreshDetails()
        assertTrue(this.presenterSpy.presentRemoveErrorCalled)
    }

    @Test
    fun testShouldRefreshDetailsShouldAskThePresenterToPresentRemoveNoMoreItems() {
        this.sut.shouldRefreshDetails()
        assertTrue(this.presenterSpy.presentRemoveNoMoreItemsCalled)
    }

    @Test
    fun testShouldRefreshDetailsShouldSetIsFetchingItemsToTrueForPaginationModel() {
        this.sut.paginationModel.isFetchingItems = false
        this.sut.paginationModel.noMoreItems = false
        this.sut.shouldRefreshDetails()
        assertTrue(this.sut.paginationModel.isFetchingItems)
    }

    @Test
    fun testShouldRefreshDetailsShouldAskThePresenterToPresentLoadingStateWhenItIsNotFetchingItemsAndThereAreMoreItems() {
        this.sut.paginationModel.isFetchingItems = false
        this.sut.paginationModel.noMoreItems = false
        this.sut.shouldRefreshDetails()
        assertTrue(this.presenterSpy.presentLoadingStateCalled)
    }

    @Test
    fun testShouldRefreshDetailsShouldAskTheWorkerToFetchJokesWhenItIsNotFetchingItemsAndThereAreMoreItems() {
        this.sut.paginationModel.isFetchingItems = false
        this.sut.paginationModel.noMoreItems = false
        this.sut.shouldRefreshDetails()
        assertTrue(this.workerSpy.fetchJokesCalled)
    }

    @Test
    fun testShouldSelectReadAnswerShouldUpdateReadJokesForPaginationModel() {
        val uuid = "jokeId"
        val joke = Joke()
        joke.uuid = uuid
        val jokes = arrayListOf(joke)
        this.sut.paginationModel.items = jokes
        this.sut.paginationModel.readJokes = arrayListOf()

        this.sut.shouldSelectReadAnswer(JokesModels.ItemSelection.Request(uuid))
        assertEquals(this.sut.paginationModel.readJokes.size, jokes.size)
    }

    @Test
    fun testShouldSelectReadAnswerShouldAskThePresenterToPresentReadState() {
        val uuid = "jokeId"
        val joke = Joke()
        joke.uuid = uuid
        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldSelectReadAnswer(JokesModels.ItemSelection.Request(uuid))
        assertTrue(this.presenterSpy.presentReadStateCalled)
    }

    @Test
    fun testShouldSelectLogoShouldAskThePresenterToPresentScrollToItem() {
        this.sut.shouldSelectLogo()
        assertTrue(this.presenterSpy.presentScrollToItemCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldNotAskThePresenterToPresentUserAvatarImageLoadingStateWhenThereIsAnImage() {
        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request("uuid", hasImage = true, isLoadingImage = false))
        assertFalse(this.presenterSpy.presentUserAvatarImageLoadingStateCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldNotAskTheWorkerToFetchUserAvatarImageWhenThereIsAnImage() {
        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request("uuid", hasImage = true, isLoadingImage = false))
        assertFalse(this.workerSpy.fetchUserAvatarImageCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldNotAskThePresenterToPresentUserAvatarImageLoadingStateWhenThereIsNoImageUrl() {
        val photo = Photo()
        photo.url150 = null

        val user = User()
        user.photo = photo

        val uuid = "uuid"
        val joke = Joke(uuid = uuid)
        joke.user = user

        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request("uuid", hasImage = false, isLoadingImage = false))
        assertFalse(this.presenterSpy.presentUserAvatarImageLoadingStateCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldNotAskTheWorkerToFetchUserAvatarImageWhenThereIsNoImageUrl() {
        val photo = Photo()
        photo.url150 = null

        val user = User()
        user.photo = photo

        val uuid = "uuid"
        val joke = Joke(uuid = uuid)
        joke.user = user

        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request("uuid", hasImage = false, isLoadingImage = false))
        assertFalse(this.workerSpy.fetchUserAvatarImageCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldNotAskThePresenterToPresentUserAvatarImageLoadingStateWhenIsLoadingImage() {
        val photo = Photo()
        photo.url150 = "url150"

        val user = User()
        user.photo = photo

        val uuid = "uuid"
        val joke = Joke(uuid = uuid)
        joke.user = user

        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request("uuid", hasImage = false, isLoadingImage = true))
        assertFalse(this.presenterSpy.presentUserAvatarImageLoadingStateCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldNotAskTheWorkerToFetchUserAvatarImageWhenIsLoadingImage() {
        val photo = Photo()
        photo.url150 = "url150"

        val user = User()
        user.photo = photo

        val uuid = "uuid"
        val joke = Joke(uuid = uuid)
        joke.user = user

        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request("uuid", hasImage = false, isLoadingImage = true))
        assertFalse(this.workerSpy.fetchUserAvatarImageCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldAskThePresenterToPresentUserAvatarImageLoadingState() {
        val photo = Photo()
        photo.url150 = "url150"

        val user = User()
        user.photo = photo

        val uuid = "uuid"
        val joke = Joke(uuid = uuid)
        joke.user = user

        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request(uuid, hasImage = false, isLoadingImage = false))
        assertTrue(this.presenterSpy.presentUserAvatarImageLoadingStateCalled)
    }

    @Test
    fun testShouldFetchUserAvatarImageShouldAskTheWorkerToFetchUserAvatarImage() {
        val photo = Photo()
        photo.url150 = "url150"

        val user = User()
        user.photo = photo

        val uuid = "uuid"
        val joke = Joke(uuid = uuid)
        joke.user = user

        this.sut.paginationModel.items = arrayListOf(joke)

        this.sut.shouldFetchUserAvatarImage(JokesModels.UserAvatarImage.Request(uuid, hasImage = false, isLoadingImage = false))
        assertTrue(this.workerSpy.fetchUserAvatarImageCalled)
    }

    @Test
    fun testSuccessDidFetchUserAvatarImageShouldAskThePresenterToPresentUserAvatarImageLoadingState() {
        this.sut.successDidFetchUserAvatarImage("uuid", null)
        assertTrue(this.presenterSpy.presentUserAvatarImageLoadingStateCalled)
    }

    @Test
    fun testSuccessDidFetchUserAvatarImageShouldAskThePresenterToPresentUserAvatarImage() {
        this.sut.successDidFetchUserAvatarImage("uuid", null)
        assertTrue(this.presenterSpy.presentUserAvatarImageCalled)
    }

    @Test
    fun testFailureDidFetchUserAvatarImageShouldAskThePresenterToPresentUserAvatarImageLoadingState() {
        this.sut.failureDidFetchUserAvatarImage("uuid", OperationError.noDataAvailable)
        assertTrue(this.presenterSpy.presentUserAvatarImageLoadingStateCalled)
    }
}
