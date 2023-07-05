package dev.beewise.jokes.scenes.jokes

import dev.beewise.jokes.operations.base.errors.OperationError
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesDisplayLogicSpy
import dev.beewise.jokes.test_cases.RobolectricTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class JokesPresenterTests : RobolectricTestCase() {
    lateinit var sut: JokesPresenter
    lateinit var displayerSpy: JokesDisplayLogicSpy

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
        this.displayerSpy = JokesDisplayLogicSpy()
        this.sut = JokesPresenter(this.displayerSpy)
    }
    
    @Test
    fun testPresentLoadingStateShouldAskTheDisplayerToDisplayLoadingState() {
        this.sut.presentLoadingState()
        assertTrue(this.displayerSpy.displayLoadingStateCalled)
    }

    @Test
    fun testPresentNotLoadingStateShouldAskTheDisplayerToDisplayNotLoadingState() {
        this.sut.presentNotLoadingState()
        assertTrue(this.displayerSpy.displayNotLoadingStateCalled)
    }

    @Test
    fun testPresentItemsShouldAskTheDisplayerToDisplayItems() {
        this.sut.presentItems(JokesModels.ItemsPresentation.Response(arrayListOf(), arrayListOf()))
        assertTrue(this.displayerSpy.displayItemsCalled)
    }

    @Test
    fun testPresentNoMoreItemsShouldAskTheDisplayerToDisplayNoMoreItems() {
        this.sut.presentNoMoreItems()
        assertTrue(this.displayerSpy.displayNoMoreItemsCalled)
    }

    @Test
    fun testPresentRemoveNoMoreItemsShouldAskTheDisplayerToDisplayRemoveNoMoreItems() {
        this.sut.presentRemoveNoMoreItems()
        assertTrue(this.displayerSpy.displayRemoveNoMoreItemsCalled)
    }

    @Test
    fun testPresentErrorShouldAskTheDisplayerToDisplayError() {
        this.sut.presentError(JokesModels.ErrorPresentation.Response(OperationError.noDataAvailable))
        assertTrue(this.displayerSpy.displayErrorCalled)
    }

    @Test
    fun testPresentRemoveErrorShouldAskTheDisplayerToDisplayRemoveError() {
        this.sut.presentRemoveError()
        assertTrue(this.displayerSpy.displayRemoveErrorCalled)
    }

    @Test
    fun testPresentReadStateShouldAskTheDisplayerToDisplayReadState() {
        this.sut.presentReadState(JokesModels.ItemReadState.Response(true, "id"))
        assertTrue(this.displayerSpy.displayReadStateCalled)
    }

    @Test
    fun testPresentScrollToItemShouldAskTheDisplayerToDisplayScrollToItem() {
        this.sut.presentScrollToItem(JokesModels.ItemScroll.Response(false, 0))
        assertTrue(this.displayerSpy.displayScrollToItemCalled)
    }

    @Test
    fun testPresentUserAvatarImageShouldAskTheDisplayerToDisplayUserAvatarImage() {
        this.sut.presentUserAvatarImage(JokesModels.UserAvatarImage.Response("uuid", null))
        assertTrue(this.displayerSpy.displayUserAvatarImageCalled)
    }

    @Test
    fun testPresentUserAvatarImageLoadingStateShouldAskTheDisplayerToDisplayUserAvatarImageLoadingState() {
        this.sut.presentUserAvatarImageLoadingState(JokesModels.UserAvatarImageLoadingState.Response("id", true))
        assertTrue(this.displayerSpy.displayUserAvatarImageLoadingStateCalled)
    }
}