package com.example.makeyourcs.ui.user.management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourcs.R
import kotlinx.android.synthetic.main.accountlist_item.view.*

class AccountMgtRecyclerAdapter(private val items: ArrayList<AccountMgtItem>) :
    RecyclerView.Adapter<AccountMgtRecyclerAdapter.ViewHolder>(){

    // 보여줄 아이템 개수가 몇 개인지
    override fun getItemCount(): Int = items.size

    // 생성된 View에 보여줄 데이터를 설정(set)해준다 -> View가 생성되면 호출된다.
    override fun onBindViewHolder(holder: AccountMgtRecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
        }
        holder.apply{
            bind(listener, item)
            itemView.tag = item
        }
    }

    // 보여줄 아이템 개수만큼 View를 생성한다 -> RecyclerView가 초기화될 때 호출된다.
    // 여기서 전에 구현한 item.xml을 View로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AccountMgtRecyclerAdapter.ViewHolder{
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.accountlist_item, parent, false)
        return AccountMgtRecyclerAdapter.ViewHolder(inflatedView)
    }

    // ViewHolder 단위 객체로 View의 데이터를 설정한다
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view : View = v

//        private val accimg = view.findViewById<ImageView>(R.id.acc_image)
//        private val accname = view.findViewById<TextView>(R.id.acc_name)

        fun bind(listener: View.OnClickListener, item:AccountMgtItem){
            view.acc_image.setImageDrawable(item.image)
            view.acc_name.text = item.name
            view.setOnClickListener(listener)
        }
    }
}