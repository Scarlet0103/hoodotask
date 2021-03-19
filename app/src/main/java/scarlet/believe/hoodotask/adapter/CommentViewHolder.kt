package scarlet.believe.hoodotask.adapter

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import scarlet.believe.hoodotask.R
import scarlet.believe.hoodotask.data.model.CommentData


class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(comment: CommentData?) {
        if (comment != null) {
            val name = comment.owner.firstName
            val message = name + " " + comment.message
            val ss = SpannableString(message)
            val boldSpan = StyleSpan(Typeface.BOLD)
            ss.setSpan(boldSpan,0,name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            itemView.findViewById<TextView>(R.id.txt_comment).text = ss
            val imageView = itemView.findViewById<CircleImageView>(R.id.img_owner_comment)
            if (!comment.owner.picture.isNullOrEmpty())
                Picasso.get().load(comment.owner.picture).into(imageView)
        }
    }

    companion object {
        fun create(parent: ViewGroup): CommentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment, parent, false)
            return CommentViewHolder(view)
        }
    }
}