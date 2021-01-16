package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.content.ContextCompat

class AccountManagementActivity : AppCompatActivity() {
    val LIST_MENU = arrayOf("List1", "List2", "List3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        val listview: ListView
        val adapter: AccountListviewAdapter

        adapter = AccountListviewAdapter()

        listview = findViewById<View>(R.id.AccountListview) as ListView
        listview.adapter = adapter

        // 첫 번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile_oval)!!, "본 계정 (현 계정)")

        // 두 번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile_oval)!!, "부캐명 1")

        // 세 번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile_oval)!!, "부캐명 2")


        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의
        listview.onItemClickListener = AdapterView.OnItemClickListener{parent, v, position, id ->
            // get item
            val item = parent.getItemAtPosition(position) as AccountListviewItem
            val img = item.accountImg
            val name = item.accountName
        }

//        // listview 클릭 이벤트 추가시
//        listview.onItemClickListener = object : AdapterView.OnItemClickListener{
//            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//
//                val strText = parent.getItemAtPosition(position) as String //ListView아이템인 Textview의 텍스트 가져오기
//            }
//        }

        listview.setOnLongClickListener(object: View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean{


                return false
            }
        })
    }

}