package com.example.makeyourcs.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R

class RecyclerFeedAdapter(private val context: Context, private val dataList: ArrayList<ImageVo>)
    : RecyclerView.Adapter<RecyclerFeedAdapter.ItemViewHolder>() {

    var mPosition = 0

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userPhoto = itemView.findViewById<ImageView>(R.id.img)
        private val userName = itemView.findViewById<TextView>(R.id.info)

        fun bind(imagevo: ImageVo, context: Context) {
            if (imagevo.photo != "") {
                val resourceId =
                    context.resources.getIdentifier(imagevo.photo, "drawable", context.packageName)

                if (resourceId > 0) {
                    userPhoto.setImageResource(resourceId)
                } else {
                    userPhoto.setImageResource(R.mipmap.ic_launcher_round)
                }
            } else {
                userPhoto.setImageResource(R.mipmap.ic_launcher_round)
            }

            //TextView에 데이터 세팅
            userName.text = imagevo.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)

        holder.itemView.setOnClickListener { view ->
            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()

            // open another activity on item click
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra("image_name", dataList[position].photo) // put image data in Intent
            context.startActivity(intent) // start Intent
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