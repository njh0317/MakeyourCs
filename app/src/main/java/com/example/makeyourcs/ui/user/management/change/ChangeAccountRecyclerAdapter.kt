package com.example.makeyourcs.ui.user.management.change

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.user.management.AccountMgtItem
import kotlinx.android.synthetic.main.accountlist_item.view.*

class ChangeAccountRecyclerAdapter(
    private val data: ArrayList<AccountMgtItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ChangeAccountRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val img = itemView.acc_image
        var tv = itemView.acc_name

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    // 보여줄 아이템 개수가 몇 개인지
    override fun getItemCount(): Int{
        return data.size
    }

    // 생성된 View에 보여줄 데이터를 설정(set)해준다 -> View가 생성되면 호출된다.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       data.get(position).let{ item ->
           with(holder){
               tv.text = item.name + " (" + item.groupname + ")"
               img.setImageResource(item.image)
           }

       }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.accountlist_item,
            parent,
            false
        )
        return MyViewHolder(itemView)
    }
}