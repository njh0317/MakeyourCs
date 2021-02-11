package com.example.makeyourcs.ui.user.management.follower

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.user.management.AccountMgtRecyclerAdapter
import kotlinx.android.synthetic.main.followerlist_item.view.*

class FollowerRecyclerAdapter(
    private val data: ArrayList<FollowerItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<FollowerRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val img = itemView.follower_img
        val tv = itemView.follower_id

        init{
            itemView.plusbtn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                if (v != null) {
                    listener.onItemClick(position, v)
                }
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.followerlist_item,
            parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        data.get(position).let{ item ->
            with(holder){
                tv.text = item.id
                img.setImageResource(item.image)

            }
        }
    }
}