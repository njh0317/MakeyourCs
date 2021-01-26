package com.example.makeyourcs.ui.user.management

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.AccountlistItemBinding
import kotlinx.android.synthetic.main.accountlist_item.view.*
//private val items: ArrayList<AccountMgtItem>
class AccountMgtRecyclerAdapter() :
    RecyclerView.Adapter<AccountMgtRecyclerAdapter.ViewHolder>(){
    private var sitems: ArrayList<AccountMgtItem> = arrayListOf()
    // 보여줄 아이템 개수가 몇 개인지
//**    override fun getItemCount(): Int = items.size
    override fun getItemCount(): Int{
        return sitems.size
    }

    // 생성된 View에 보여줄 데이터를 설정(set)해준다 -> View가 생성되면 호출된다.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = items[position]
        val item = sitems[position]
        holder.apply{
            bind(item)
            itemView.tag = item
        }
    }

    // 보여줄 아이템 개수만큼 View를 생성한다 -> RecyclerView가 초기화될 때 호출된다.
    // 여기서 전에 구현한 item.xml을 View로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder{
        return ViewHolder(
          DataBindingUtil.inflate(
              LayoutInflater.from(parent.context), R.layout.accountlist_item, parent, false
          )
        )
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정한다
    class ViewHolder(
        private val binding: AccountlistItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AccountMgtItem){
            binding.apply{
                accountItem = item
            }
        }
    }

    fun setItems(items: ArrayList<AccountMgtItem>){
        Log.d("RECYCLERADAPTER", "!!setItems")
        this.sitems = items
        this.notifyDataSetChanged()
    }

}