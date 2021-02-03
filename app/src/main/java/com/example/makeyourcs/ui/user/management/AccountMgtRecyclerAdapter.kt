package com.example.makeyourcs.ui.user.management

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.AccountlistItemBinding
import com.example.makeyourcs.generated.callback.OnClickListener
import com.example.makeyourcs.ui.DetailedActivity
import kotlinx.android.synthetic.main.accountlist_item.view.*

class AccountMgtRecyclerAdapter(
    private val data: ArrayList<AccountMgtItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<AccountMgtRecyclerAdapter.MyViewHolder>(){

//    inner class MyViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
//        LayoutInflater.from(parent.context).inflate(R.layout.accountlist_item, parent, false)
//    ){
//        var img = itemView.acc_image
//        var tv = itemView.acc_name
//    }
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val img = itemView.acc_image
        var tv = itemView.acc_name

        init{
            itemView.setOnClickListener { this }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    // 보여줄 아이템 개수가 몇 개인지
    override fun getItemCount(): Int{
//        Log.e("dataSize", "" + data.size)
        return data.size
    }

    // 생성된 View에 보여줄 데이터를 설정(set)해준다 -> View가 생성되면 호출된다.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = data[position]

       data.get(position).let{ item ->
           with(holder){
               tv.text = item.name + " (" + item.groupname + ")"
               img.setImageResource(item.image)
//               itemView.setOnClickListener {
//                   itemClickListener.onClick(it, position)
//               }

           }

       }
    }

//    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
//        val item = data[position]
//        holder.apply {
//            bind(item)
//            itemView.tag = item
//        }
//    }

    // 보여줄 아이템 개수만큼 View를 생성한다 -> RecyclerView가 초기화될 때 호출된다.
    // 여기서 전에 구현한 item.xml을 View로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.accountlist_item, parent, false) //check

        return MyViewHolder(itemView)
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
//        return MyViewHolder(
//            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.accountlist_item, parent, false)
//        )
//    }
}