package scarlet.believe.hoodotask.adapter

import android.media.Image
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import scarlet.believe.hoodotask.R
import scarlet.believe.hoodotask.data.State
import scarlet.believe.hoodotask.data.model.Data
import scarlet.believe.hoodotask.utils.RecyclerVewClickListner

class PostListAdapter(private val retry: () -> Unit,private val lister : RecyclerVewClickListner)
    : PagedListAdapter<Data, RecyclerView.ViewHolder>(PostDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) PostViewHolder.create(parent) else FooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE){
            (holder as PostViewHolder).bind(getItem(position))
            holder.itemView.findViewById<ImageView>(R.id.post_comment).setOnClickListener{
                lister.onRecyclerViewItemClick(it,getItem(position)?.id!!)
            }
        }
        else (holder as FooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val PostDiffCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.text == newItem.text
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
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