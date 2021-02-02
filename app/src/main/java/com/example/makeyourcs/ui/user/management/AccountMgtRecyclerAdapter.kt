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
import com.example.makeyourcs.ui.DetailedActivity
import kotlinx.android.synthetic.main.accountlist_item.view.*

class AccountMgtRecyclerAdapter(private val data: ArrayList<AccountMgtItem>) :
    RecyclerView.Adapter<AccountMgtRecyclerAdapter.MyViewHolder>(){

//    // 클릭 인터페이스 정의
//    interface ItemClickListener{
//        fun onClick(view: View, position: Int)
//    }
//
//    //클릭 리스너 선언
//    private lateinit var itemClickListener: ItemClickListener
//
//    //클릭리스너 등록 메소드
//    fun setItemClickListener(itemClickListener: ItemClickListener){
//        this.itemClickListener = itemClickListener
//    }

    inner class MyViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.accountlist_item, parent, false)
    ){
        var img = itemView.acc_image
        var tv = itemView.acc_name
    }
//    inner class MyViewHolder(
//        private val binding: AccountlistItemBinding
//    ): RecyclerView.ViewHolder(binding.root){
//
//        fun bind(item: AccountMgtItem){
//            binding.apply {
//                accountItem = item
//            }
//        }
//    }

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
        return MyViewHolder(parent)
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
//        return MyViewHolder(
//            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.accountlist_item, parent, false)
//        )
//    }
}