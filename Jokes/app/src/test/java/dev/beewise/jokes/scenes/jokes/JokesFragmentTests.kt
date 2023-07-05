package dev.beewise.jokes.scenes.jokes

/*
import android.graphics.Bitmap
import android.os.Looper
import android.text.SpannableString
import android.widget.ImageView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.platform.app.InstrumentationRegistry
import dev.beewise.jokes.R
import dev.beewise.jokes.components.cells.JokeQuestionAnswerCell
import dev.beewise.jokes.components.cells.JokeTextCell
import dev.beewise.jokes.components.cells.SpaceCell
import dev.beewise.jokes.components.views.LoadingImageView
import dev.beewise.jokes.components.views.LogoNavigationView
import dev.beewise.jokes.components.views.UserAvatarView
import dev.beewise.jokes.models.image.CompoundImage
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesAdapterSpy
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesBusinessLogicSpy
import dev.beewise.jokes.scenes.jokes.test_doubles.JokesFragmentDelegateSpy
import dev.beewise.jokes.spies.RecyclerViewSpy
import dev.beewise.jokes.test_cases.RobolectricTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.robolectric.Shadows
import java.lang.ref.WeakReference

class JokesFragmentTests : RobolectricTestCase() {
    lateinit var sut: JokesFragment
    lateinit var interactorSpy: JokesBusinessLogicSpy
    lateinit var delegateSpy: JokesFragmentDelegateSpy
    lateinit var adapterSpy: JokesAdapterSpy
    lateinit var recyclerViewSpy: RecyclerViewSpy

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
        val scenario = launchFragmentInContainer<JokesFragment>(themeResId = R.style.Theme_Jokes)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment {
            this.sut = it
        }
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        this.interactorSpy = JokesBusinessLogicSpy()
        this.sut.interactor = this.interactorSpy

        this.delegateSpy = JokesFragmentDelegateSpy()
        this.sut.delegate = WeakReference(this.delegateSpy)
        
        this.adapterSpy = JokesAdapterSpy(this.sut)
        this.sut.adapter = this.adapterSpy

        this.recyclerViewSpy = RecyclerViewSpy(InstrumentationRegistry.getInstrumentation().targetContext)
        this.sut.recyclerView = this.recyclerViewSpy
    }

    @Test
    fun testOnCreateShouldAskTheInteractorToFetchJokes() {
        this.sut.onCreate(null)
        assertTrue(this.interactorSpy.shouldFetchJokesCalled)
    }

    @Test
    fun testShouldSelectLogoShouldAskTheInteractorToSelectLogo() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        this.sut.logoNavigationViewOnPressLogoImage(LogoNavigationView(context))
        this.waitForUIThread()
        assertTrue(this.interactorSpy.shouldSelectLogoCalled)
    }

    @Test
    fun testJokeQuestionAnswerCellOnPressReadAnswerShouldAskTheInteractorToSelectReadAnswer() {
        this.sut.jokesAdapterJokeQuestionAnswerCellOnPressReadAnswer(this.adapterSpy, "id")
        assertTrue(interactorSpy.shouldSelectReadAnswerCalled)
    }

    @Test
    fun testDisplayLoadingStateShouldUpdateIsLoading() {
        this.sut.adapter.isLoading = false
        this.sut.displayLoadingState()
        this.waitForUIThread()
        assertTrue(this.sut.adapter.isLoading)
        assertTrue(this.adapterSpy.setNewIsLoadingCalled)
    }

    @Test
    fun testDisplayNotLoadingStateShouldUpdateIsLoading() {
        this.sut.adapter.isLoading = true
        this.sut.displayNotLoadingState()
        this.waitForUIThread()
        assertFalse(this.sut.adapter.isLoading)
        assertTrue(this.adapterSpy.setNewIsLoadingCalled)
    }

    @Test
    fun testDisplayItemsShouldUpdateDisplayedItems() {
        val items = arrayListOf<Any>(JokesModels.DisplayedItem("uuid", JokesModels.ItemType.jokeText, null))
        this.sut.adapter.items = items
        this.sut.displayItems(JokesModels.ItemsPresentation.ViewModel(items))
        this.waitForUIThread()
        assertEquals(this.sut.adapter.items.size, items.size)
        assertTrue(this.adapterSpy.setNewItemsCalled)
    }

    @Test
    fun testDisplayNoMoreItemsShouldUpdateNoMoreItems() {
        this.sut.adapter.noMoreItemsText = null
        this.sut.adapter.noMoreItems = false
        val text = SpannableString("text")
        this.sut.displayNoMoreItems(JokesModels.NoMoreItemsPresentation.ViewModel(text))
        this.waitForUIThread()
        assertTrue(this.sut.adapter.noMoreItems)
        assertEquals(this.sut.adapter.noMoreItemsText, text)
        assertTrue(this.adapterSpy.setNewNoMoreItemsCalled)
    }

    @Test
    fun testDisplayRemoveNoMoreItemsShouldUpdateNoMoreItems() {
        this.sut.adapter.noMoreItemsText = SpannableString("text")
        this.sut.adapter.noMoreItems = true
        this.sut.displayRemoveNoMoreItems()
        this.waitForUIThread()
        assertFalse(this.sut.adapter.noMoreItems)
        assertNull(this.sut.adapter.noMoreItemsText)
        assertTrue(this.adapterSpy.setNewNoMoreItemsCalled)
    }

    @Test
    fun testDisplayErrorShouldUpdateError() {
        this.sut.adapter.errorText = null
        this.sut.adapter.hasError = false
        val text = SpannableString("text")
        this.sut.displayError(JokesModels.ErrorPresentation.ViewModel(text))
        this.waitForUIThread()
        assertTrue(this.sut.adapter.hasError)
        assertEquals(this.sut.adapter.errorText, text)
        assertTrue(this.adapterSpy.setNewHasErrorCalled)
    }

    @Test
    fun testDisplayRemoveErrorShouldUpdateError() {
        this.sut.adapter.errorText = SpannableString("text")
        this.sut.adapter.hasError = true
        this.sut.displayRemoveError()
        this.waitForUIThread()
        assertFalse(this.sut.adapter.hasError)
        assertNull(this.sut.adapter.errorText)
        assertTrue(this.adapterSpy.setNewHasErrorCalled)
    }

    @Test
    fun testDisplayReadStateShouldUpdateIsReadForJokeQnaModel() {
        val isRead = true
        val uuid = "jokeId"

        val model = JokeQuestionAnswerCell.Model(UserAvatarView.Model(LoadingImageView.Model(CompoundImage(null, null, ImageView.ScaleType.CENTER_CROP), false)))
        model.isRead = false
        model.id = uuid

        val item = JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeQna, model = model)
        this.sut.adapter.items = arrayListOf(item)

        this.sut.displayReadState(JokesModels.ItemReadState.ViewModel(isRead, uuid))
        this.waitForUIThread()
        assertEquals(model.isRead, isRead)
    }

    @Test
    fun testDisplayScrollToItemShouldAskTheRecyclerViewToScrollToPosition() {
        this.sut.adapter.items = arrayListOf(JokesModels.DisplayedItem("uuid", JokesModels.ItemType.space, SpaceCell.Model(50)))
        this.sut.displayScrollToItem(JokesModels.ItemScroll.ViewModel(false, 0))
        this.waitForUIThread()
        assertTrue(this.recyclerViewSpy.scrollToPositionCalled)
    }

    @Test
    fun testDisplayUserAvatarImageShouldUpdateImageForJokeTextModel() {
        val uuid = "jokeId"

        val model = JokeTextCell.Model(UserAvatarView.Model(LoadingImageView.Model(CompoundImage(null, null, ImageView.ScaleType.CENTER_CROP), false)))
        model.id = uuid

        val item = JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeText, model = model)
        this.sut.adapter.items = arrayListOf(item)

        val bitmap = this.emptyBitmap()
        this.sut.displayUserAvatarImage(JokesModels.UserAvatarImage.ViewModel(uuid, bitmap))
        this.waitForUIThread()
        assertEquals(model.avatar.loadingImage.image.bitmap, bitmap)
        assertNotNull(model.avatar.loadingImage.image.bitmap)
    }

    @Test
    fun testDisplayUserAvatarImageShouldUpdateImageForJokeQnaModel() {
        val uuid = "jokeId"

        val model = JokeQuestionAnswerCell.Model(UserAvatarView.Model(LoadingImageView.Model(CompoundImage(null, null, ImageView.ScaleType.CENTER_CROP), false)))
        model.id = uuid

        val item = JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeQna, model = model)
        this.sut.adapter.items = arrayListOf(item)

        val bitmap = this.emptyBitmap()
        this.sut.displayUserAvatarImage(JokesModels.UserAvatarImage.ViewModel(uuid, bitmap))
        this.waitForUIThread()
        assertEquals(model.avatar.loadingImage.image.bitmap, bitmap)
        assertNotNull(model.avatar.loadingImage.image.bitmap)
    }

    @Test
    fun testDisplayUserAvatarImageLoadingStateShouldUpdateIsLoadingImageForJokeTextModel() {
        val uuid = "jokeId"

        val model = JokeTextCell.Model(UserAvatarView.Model(LoadingImageView.Model(CompoundImage(null, null, ImageView.ScaleType.CENTER_CROP), false)))
        model.id = uuid

        val item = JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeText, model = model)
        this.sut.adapter.items = arrayListOf(item)

        val isLoadingImage = true
        this.sut.displayUserAvatarImageLoadingState(JokesModels.UserAvatarImageLoadingState.ViewModel(uuid, isLoadingImage))
        this.waitForUIThread()
        assertEquals(model.avatar.loadingImage.isLoading, isLoadingImage)
    }

    @Test
    fun testDisplayUserAvatarImageLoadingStateShouldUpdateIsLoadingImageForJokeQnaModel() {
        val uuid = "jokeId"

        val model = JokeQuestionAnswerCell.Model(UserAvatarView.Model(LoadingImageView.Model(CompoundImage(null, null, ImageView.ScaleType.CENTER_CROP), false)))
        model.id = uuid

        val item = JokesModels.DisplayedItem(type = JokesModels.ItemType.jokeQna, model = model)
        this.sut.adapter.items = arrayListOf(item)

        val isLoadingImage = true
        this.sut.displayUserAvatarImageLoadingState(JokesModels.UserAvatarImageLoadingState.ViewModel(uuid, isLoadingImage))
        this.waitForUIThread()
        assertEquals(model.avatar.loadingImage.isLoading, isLoadingImage)
    }

    private fun emptyBitmap(): Bitmap {
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}
*/