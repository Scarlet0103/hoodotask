package scarlet.believe.hoodotask.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import scarlet.believe.hoodotask.data.State
import scarlet.believe.hoodotask.data.model.CommentData

class CommentListAdapter(private val retry: () -> Unit)
    : PagedListAdapter<CommentData, RecyclerView.ViewHolder>(PostDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) CommentViewHolder.create(parent) else FooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as CommentViewHolder).bind(getItem(position))
        else (holder as FooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val PostDiffCallback = object : DiffUtil.ItemCallback<CommentData>() {
            override fun areItemsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
                return oldItem.message == newItem.message
            }

            override fun areContentsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}