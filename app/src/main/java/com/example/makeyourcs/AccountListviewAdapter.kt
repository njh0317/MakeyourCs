package com.example.makeyourcs

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView

// AccountListviewAdapter의 생성자
class AccountListviewAdapter : BaseAdapter() {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private var listViewItemList = ArrayList<AccountListviewItem>()

    // Adapter에 사용되는 데이터의 개수를 리턴: 필수 구현
    override fun getCount(): Int {
        return listViewItemList.size
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴: 필수 구현
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val context = parent.context

        // "listview_item" Layout을 inflate하여 convertView 참조 획득 (Inflate는 xml에 표기된 레이아웃들을 메모리에 객체화시키는 행동)
        if(view == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view  = inflater.inflate(R.layout.account_listview_item, parent, false)
        }

        // 화면에 표시될 View(Layout이 inflate됨)으로부터 위젯에 대한 참조 획득
        val accountImgView = view!!.findViewById(R.id.profile_image) as ImageView
        val accountNameView = view.findViewById(R.id.account_name) as TextView

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        val listViewItem = listViewItemList[position]

        //아이템 내 각 위젯에 데이터 반영
        accountImgView.setImageDrawable(listViewItem.accountImg)
        accountNameView.setText(listViewItem.accountName)

        return view
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴: 필수 구현
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // 지정한 위치(position)에 있는 데이터 리턴: 필수 구현
    override fun getItem(position: Int): Any {
        return listViewItemList[position]
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    fun addItem(accImg:Drawable, accName: String){
        val item = AccountListviewItem()

        item.accountImg = accImg
        item.accountName = accName

        listViewItemList.add(item)
    }
}