package scarlet.believe.hoodotask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import scarlet.believe.hoodotask.R
import scarlet.believe.hoodotask.data.State

class FooterViewHolder(veiw : View) : RecyclerView.ViewHolder(veiw) {

    fun bind(status: State?) {
        itemView.findViewById<ProgressBar>(R.id.footer_progress_bar).visibility = if (status == State.LOADING) View.VISIBLE else View.INVISIBLE
        itemView.findViewById<TextView>(R.id.footer_txt_error).visibility = if (status == State.ERROR) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): FooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.findViewById<TextView>(R.id.footer_txt_error)
            return FooterViewHolder(view)
        }
    }
}