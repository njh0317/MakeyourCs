package com.example.makeyourcs.ui.user.management

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.databinding.AccountlistItemBinding
import kotlinx.android.synthetic.main.accountlist_item.view.*

class AccountMgtRecyclerAdapter(var data: ArrayList<AccountMgtItem>) :
    RecyclerView.Adapter<AccountMgtRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.accountlist_item, parent, false)
    ){
        var img = itemView.acc_image
        var tv = itemView.acc_name
    }

    // 보여줄 아이템 개수가 몇 개인지
    override fun getItemCount(): Int{
//        Log.e("dataSize", "" + data.size)
        return data.size
    }

    // 생성된 View에 보여줄 데이터를 설정(set)해준다 -> View가 생성되면 호출된다.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = items[position]
       data.get(position).let{ item ->
           with(holder){
               if(position == 0){
                   tv.text = item.name + " (본 계정)"
               }else{
                   tv.text = item.name
               }
               img.setImageResource(item.image)
//               tv.text = item.name
//               Log.e("Img&TextSet", tv.text.toString())
           }
       }
    }

    // 보여줄 아이템 개수만큼 View를 생성한다 -> RecyclerView가 초기화될 때 호출된다.
    // 여기서 전에 구현한 item.xml을 View로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder{
        return MyViewHolder(parent)
    }

}