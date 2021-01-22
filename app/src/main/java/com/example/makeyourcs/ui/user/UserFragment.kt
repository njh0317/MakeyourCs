package com.example.makeyourcs.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.RecyclerFeedAdapter

class UserFragment : Fragment(){
    var userList = arrayListOf<ImageVo>(
        ImageVo("1",  "ic_1"),
        ImageVo("2",  "ic_2"),
        ImageVo("3", "ic_3"),
        ImageVo("4",  "ic_4"),
        ImageVo("5",  "ic_5"),
        ImageVo("6",  "ic_6"),
        ImageVo("7",  "ic_7"),
        ImageVo("8",  "ic_8"),
        ImageVo("9",  "ic_9"),
        ImageVo("10",  "ic_10"),
        ImageVo("11",  "ic_11"),
        ImageVo("12",  "ic_1"),
        ImageVo("13",  "ic_2"),
        ImageVo("14", "ic_3"),
        ImageVo("15", "ic_4")
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=LayoutInflater.from(activity).inflate(R.layout.fragment_user,container,false)

        val mAdapter=
            RecyclerFeedAdapter(view.context, userList)
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        val rview=view.findViewById<RecyclerView>(R.id.user_recycler)
        rview.adapter=mAdapter
        rview.layoutManager=lmanager

        return view;
    }
}