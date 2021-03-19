package scarlet.believe.hoodotask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import scarlet.believe.hoodotask.R
import scarlet.believe.hoodotask.data.model.Data


class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(post: Data?) {
        if (post != null) {
            itemView.findViewById<TextView>(R.id.txt_owner_name).text = "${post.owner.firstName} ${post.owner.lastName}"
            itemView.findViewById<TextView>(R.id.txt_post_title).text = post.text
            itemView.findViewById<TextView>(R.id.txt_post_likes).text =  "${post.likes} likes"
            itemView.findViewById<TextView>(R.id.txt_post_time).text = post.publishDate

            val tags = post.tags
            var s : String = ""
            for(i in tags){
                s = "$s#$i "
            }
            itemView.findViewById<TextView>(R.id.txt_post_tags).text = s
            val imageview = itemView.findViewById<ImageView>(R.id.img_post)
            val imageView2 = itemView.findViewById<CircleImageView>(R.id.img_owner_post)
            if (!post.image.isNullOrEmpty())
                Picasso.get().load(post.image).into(imageview)
            if (!post.owner.picture.isNullOrEmpty())
                Picasso.get().load(post.owner.picture).into(imageView2)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_post, parent, false)
            return PostViewHolder(view)
        }
    }

}