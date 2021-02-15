package com.example.makeyourcs.ui.user.management.follower

import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.user.management.AccountMgtRecyclerAdapter
import kotlinx.android.synthetic.main.followerlist_item.view.*
//,
//private val listener: FollowerRecyclerAdapter.OnItemClickListener
class FollowerRecyclerAdapter(
    private val data: ArrayList<FollowerItem>
) :
    RecyclerView.Adapter<FollowerRecyclerAdapter.MyViewHolder>(){

    var SelectedItems = SparseBooleanArray(0)

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
//                    listener.onItemClick(position, v)
                    if(SelectedItems.get(position, false)){
                        SelectedItems.put(position, false)
                        v.plusbtn.setBackgroundResource(R.drawable.orangebtn)
                        v.plusbtn.setText("추가")
                        v.plusbtn.setTextColor(Color.WHITE)
                        Log.d("inFollowerMainActivity", "$position 취소됨")
                    }else {
                        SelectedItems.put(position, true)
                        v.plusbtn.setBackgroundResource(R.drawable.whitebtn)
                        v.plusbtn.setText("취소")
                        v.plusbtn.setTextColor(Color.BLACK)
                        Log.d("inFollowerMainActivity", "$position 추가됨")
                    }
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

                if(SelectedItems.get(position, false)){
                    holder.itemView.plusbtn.setBackgroundResource(R.drawable.whitebtn)
                    holder.itemView.plusbtn.setText("취소")
                    holder.itemView.plusbtn.setTextColor(Color.BLACK)
                }else {
                    holder.itemView.plusbtn.setBackgroundResource(R.drawable.orangebtn)
                    holder.itemView.plusbtn.setText("추가")
                    holder.itemView.plusbtn.setTextColor(Color.WHITE)
                }
            }
        }
    }
}