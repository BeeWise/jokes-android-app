package dev.beewise.jokes.scenes.jokes

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R
import dev.beewise.jokes.components.adapters.FetchingItemsRecyclerViewAdapter
import dev.beewise.jokes.components.cells.*
import dev.beewise.jokes.extensions.view_group.inflateView
import java.lang.ref.WeakReference

interface JokesAdapterDelegate {
    fun jokesAdapterJokeQuestionAnswerCellOnPressReadAnswer(adapter: JokesAdapter, id: String?)
    fun jokesAdapterJokeQuestionAnswerCellShouldFetchUserAvatarImage(adapter: JokesAdapter, id: String?, hasImage: Boolean, isLoadingImage: Boolean)
    fun jokesAdapterJokeTextCellShouldFetchUserAvatarImage(adapter: JokesAdapter, id: String?, hasImage: Boolean, isLoadingImage: Boolean)
}

open class JokesAdapter(delegate: JokesAdapterDelegate) : FetchingItemsRecyclerViewAdapter(), JokeTextCellDelegate, JokeQuestionAnswerCellDelegate {
    var delegate: WeakReference<JokesAdapterDelegate>? = null

    override var loadingColor: Int = JokesStyle.instance.recyclerViewModel.activityIndicatorColor()

    init {
        this.delegate = WeakReference(delegate)
    }

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CellType.jokeText.value -> JokeTextCell(parent.inflateView(R.layout.cell))
            CellType.jokeQna.value -> JokeQuestionAnswerCell(parent.inflateView(R.layout.cell))
            CellType.space.value -> SpaceCell(parent.inflateView(R.layout.cell))
            else -> Cell(parent)
        }
    }

    override fun onBindCellViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CellType.jokeText.value -> this.onBindJokeTextCell(holder as JokeTextCell, position)
            CellType.jokeQna.value -> this.onBindJokeQnaCell(holder as JokeQuestionAnswerCell, position)
            CellType.space.value -> this.onBindSpaceCell(holder as SpaceCell, position)
        }
    }

    override fun getItemCellType(position: Int): Int {
        val displayedItem = this.items[position] as? JokesModels.DisplayedItem ?: return ItemViewType.none.value
        return when (displayedItem.type) {
            JokesModels.ItemType.jokeText -> CellType.jokeText.value
            JokesModels.ItemType.jokeQna -> CellType.jokeQna.value
            JokesModels.ItemType.space -> CellType.space.value
        }
    }

    //#region Joke text cell
    private fun onBindJokeTextCell(cell: JokeTextCell, position: Int) {
        val displayedItem = this.items[position] as? JokesModels.DisplayedItem ?: return
        val model = displayedItem.model as? JokeTextCell.Model ?: return
        cell.setModel(model)
        cell.delegate = WeakReference(this)

        this.delegate?.get()?.jokesAdapterJokeTextCellShouldFetchUserAvatarImage(this, model.id, model.avatar.loadingImage.image.bitmap != null, model.avatar.loadingImage.isLoading)
    }

    override fun jokeTextCellOnPressUserAvatar(cell: JokeTextCell, id: String?) {

    }

    override fun jokeTextCellOnPressUserName(cell: JokeTextCell, id: String?) {

    }

    override fun jokeTextCellOnPressLikeCount(cell: JokeTextCell, id: String?) {

    }

    override fun jokeTextCellOnPressDislikeCount(cell: JokeTextCell, id: String?) {

    }
    //#endregion

    //#region Joke qna cell
    private fun onBindJokeQnaCell(cell: JokeQuestionAnswerCell, position: Int) {
        val displayedItem = this.items[position] as? JokesModels.DisplayedItem ?: return
        val model = displayedItem.model as? JokeQuestionAnswerCell.Model ?: return
        cell.setModel(model)
        cell.delegate = WeakReference(this)

        this.delegate?.get()?.jokesAdapterJokeQuestionAnswerCellShouldFetchUserAvatarImage(this, model.id, model.avatar.loadingImage.image.bitmap != null, model.avatar.loadingImage.isLoading)
    }

    override fun jokeQuestionAnswerCellOnPressUserAvatar(cell: JokeQuestionAnswerCell, id: String?) {

    }

    override fun jokeQuestionAnswerCellOnPressUserName(cell: JokeQuestionAnswerCell, id: String?) {

    }

    override fun jokeQuestionAnswerCellOnPressLikeCount(cell: JokeQuestionAnswerCell, id: String?) {

    }

    override fun jokeQuestionAnswerCellOnPressDislikeCount(cell: JokeQuestionAnswerCell, id: String?) {

    }

    override fun jokeQuestionAnswerCellOnPressReadAnswer(cell: JokeQuestionAnswerCell, id: String?) {
        this.delegate?.get()?.jokesAdapterJokeQuestionAnswerCellOnPressReadAnswer(this, id)
    }
    //#endregion

    //#region Space cell
    private fun onBindSpaceCell(cell: SpaceCell, position: Int) {
        val displayedItem = this.items[position] as? JokesModels.DisplayedItem ?: return
        val model = displayedItem.model as? SpaceCell.Model ?: return
        cell.setModel(model)
    }
    //#endregion

    override fun titleHeaderFooterView(view: TitleHeaderFooterView?, didSelectTextView: TextView?) {

    }

    fun getIndexedJokeQnaModel(id: String?): Pair<Int, JokeQuestionAnswerCell.Model>? {
        this.items.forEachIndexed { index, item ->
            if (item is JokesModels.DisplayedItem && item.type == JokesModels.ItemType.jokeQna) {
                val model = item.model as? JokeQuestionAnswerCell.Model ?: return null
                if (model.id == id) {
                    return Pair(index, model)
                }
            }
        }
        return null
    }

    fun getIndexedJokeTextModel(id: String?): Pair<Int, JokeTextCell.Model>? {
        this.items.forEachIndexed { index, item ->
            if (item is JokesModels.DisplayedItem && item.type == JokesModels.ItemType.jokeText) {
                val model = item.model as? JokeTextCell.Model ?: return null
                if (model.id == id) {
                    return Pair(index, model)
                }
            }
        }
        return null
    }

    enum class CellType(val value: Int) {
        jokeText(4),
        jokeQna(5),
        space(6);
    }
}