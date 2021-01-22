package com.example.makeyourcs.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ViewItemBinding

class RecyclerFeedAdapter(private val dataList: ArrayList<ImageVo>)
    : RecyclerView.Adapter<RecyclerFeedAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imgvo: ImageVo){
            binding.apply{
                imgitem=imgvo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.view_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item=dataList[position]
        holder.apply{
            bind(item)
            itemView.tag=item
        }
//        holder.bind(dataList[position], context)
//
//        holder.itemView.setOnClickListener { view ->
//            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()
//
//            // open another activity on item click
//            val intent = Intent(context, DetailedActivity::class.java)
//            intent.putExtra("image_name", dataList[position].photo) // put image data in Intent
//            context.startActivity(intent) // start Intent
//        }
//
//        holder.itemView.setOnLongClickListener { view ->
//            Toast.makeText(view.context, "$position 아이템 롱클릭!", Toast.LENGTH_SHORT).show()
//            return@setOnLongClickListener true
//        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}