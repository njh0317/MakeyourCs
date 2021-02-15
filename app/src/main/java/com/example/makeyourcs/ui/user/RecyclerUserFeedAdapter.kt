package com.example.makeyourcs.ui.user


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.PostItemViewBinding
import com.example.makeyourcs.ui.DetailedActivity
import kotlinx.android.synthetic.main.post_item_view.view.*

class RecyclerUserFeedAdapter(private val context: Context, private val dataList: ArrayList<PostVo>)
    : RecyclerView.Adapter<RecyclerUserFeedAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: PostItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val img=binding.root.post_img

        fun bind(postvo: PostVo){
            binding.apply{
                postitem=postvo

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.post_item_view, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val post=dataList[position]
        holder.apply{
            bind(post)
            itemView.tag=post

            Glide.with(context)
                .load(post.imgUrl)
                .dontTransform()
                .into(img)

        }

        holder.itemView.setOnClickListener { view ->
            //Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()

            // open another activity on item click
           // val intent = Intent(context, DetailedActivity::class.java)
          //  intent.putExtra("image_name", dataList[position].photo) // put image data in Intent
           // context.startActivity(intent) // start Intent
        }

        holder.itemView.setOnLongClickListener { view ->
            Toast.makeText(view.context, "$position 아이템 롱클릭!", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}