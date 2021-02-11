package com.example.makeyourcs.ui.user.management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import kotlinx.android.synthetic.main.accountlist_item.view.*
import java.lang.Boolean.TRUE

class AccountMgtRecyclerAdapter(
    private val data: ArrayList<AccountMgtItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<AccountMgtRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener{
        val img = itemView.acc_image
        var tv = itemView.acc_name

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLongClick(position)
            }
            return TRUE
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLongClick(position: Int)

    }
    // 보여줄 아이템 개수가 몇 개인지
    override fun getItemCount(): Int{
//        Log.e("dataSize", "" + data.size)
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

    // 보여줄 아이템 개수만큼 View를 생성한다 -> RecyclerView가 초기화될 때 호출된다.
    // 여기서 전에 구현한 item.xml을 View로 생성
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