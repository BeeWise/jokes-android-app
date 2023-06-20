package dev.beewise.jokes.components.adapters

import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R
import dev.beewise.jokes.components.cells.LoadingHeaderFooterView
import dev.beewise.jokes.components.cells.TitleHeaderFooterView
import dev.beewise.jokes.components.cells.TitleHeaderFooterViewDelegate
import dev.beewise.jokes.extensions.view_group.inflateView
import java.lang.ref.WeakReference

open class FetchingItemsRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(), TitleHeaderFooterViewDelegate {
    open var items: ArrayList<Any> = arrayListOf()

    open var isLoading: Boolean = false
    open var loadingColor: Int = 0

    open var hasError: Boolean = false
    open var errorText: SpannableString? = null

    open var noMoreItems: Boolean = false
    open var noMoreItemsText: SpannableString? = null

    // region RecyclerView methods
    override fun getItemCount(): Int {
        if (this.items.isEmpty() && this.isLoading) return 1
        if (this.items.isEmpty() && this.hasError) return 1
        if (this.items.isEmpty() && this.noMoreItems) return 1
        if (this.items.isNotEmpty() && this.isLoading) return this.items.size + 1
        if (this.items.isNotEmpty() && this.hasError) return this.items.size + 1
        if (this.items.isNotEmpty() && this.noMoreItems) return this.items.size + 1
        return this.items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemViewType.loading.value -> LoadingHeaderFooterView(parent.inflateView(R.layout.loading_header_footer_view))
            ItemViewType.error.value -> TitleHeaderFooterView(parent.inflateView(R.layout.title_header_footer_view))
            ItemViewType.noMoreItems.value -> TitleHeaderFooterView(parent.inflateView(R.layout.title_header_footer_view))
            else -> this.onCreateCellViewHolder(parent, viewType)
        }
    }

    open fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Cell(parent)
    }

    public class Cell(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ItemViewType.loading.value -> this.onBindLoadingStateView(holder as LoadingHeaderFooterView)
            ItemViewType.error.value -> this.onBindErrorStateView(holder as TitleHeaderFooterView)
            ItemViewType.noMoreItems.value -> this.onBindNoMoreStateView(holder as TitleHeaderFooterView)
            else -> this.onBindCellViewHolder(holder, position)
        }
    }

    open fun onBindCellViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && this.isLoading) return ItemViewType.loading.value
        if (position == 0 && this.hasError) return ItemViewType.error.value
        if (position == this.items.size && this.isLoading) return ItemViewType.loading.value
        if (position == this.items.size && this.hasError) return ItemViewType.error.value
        if (position == this.items.size && this.noMoreItems) return ItemViewType.noMoreItems.value
        return this.getItemCellType(position)
    }

    open fun getItemCellType(position: Int): Int {
        return ItemViewType.none.value
    }
    // endregion

    // region Loading header footer view
    private fun onBindLoadingStateView(holder: LoadingHeaderFooterView) {
        holder.setIsLoading(this.isLoading)
        holder.setColor(this.loadingColor)
    }
    // endregion

    // region Error header footer view
    private fun onBindErrorStateView(holder: TitleHeaderFooterView) {
        holder.delegate = WeakReference(this)
        holder.setSpannableString(this.errorText)
    }

    override fun titleHeaderFooterView(view: TitleHeaderFooterView?, didSelectTextView: TextView?) {

    }
    // endregion

    // region No more items footer view
    private fun onBindNoMoreStateView(holder: TitleHeaderFooterView) {
        holder.setSpannableString(this.noMoreItemsText)
    }
    // endregion

    public enum class ItemViewType(val value: Int) {
        none(0),
        loading(1),
        error(2),
        noMoreItems(3)
    }

    // region Auxiliary
    open fun setNewIsLoading(newIsLoading: Boolean) {
        this.isLoading = newIsLoading
        this.notifyDataSetChanged()
    }

    open fun setNewHasError(newHasError: Boolean) {
        this.hasError = newHasError
        this.notifyDataSetChanged()
    }

    open fun setNewItems(newItems: ArrayList<Any>) {
        this.items = newItems
        this.notifyDataSetChanged()
    }

    open fun setNewNoMoreItems(newNoMoreItems: Boolean) {
        this.noMoreItems = newNoMoreItems
        this.notifyDataSetChanged()
    }

    open fun insertNewItems(newItems: ArrayList<Any>) {
        val positionStart = this.items.size - 1
        val itemCount = newItems.size
        this.items.addAll(newItems)
        this.notifyItemRangeInserted(positionStart, itemCount)
    }
    // endregion
}