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
    private var userList = arrayListOf<ImageVo>(
        ImageVo("1", "test3", "ic_1"),
        ImageVo("2", "test3", "ic_2"),
        ImageVo("3", "test3", "ic_3"),
        ImageVo("4", "test3", "ic_4"),
        ImageVo("5", "test3", "ic_5"),
        ImageVo("6", "test3", "ic_6"),
        ImageVo("7", "test3", "ic_7"),
        ImageVo("8", "test3", "ic_8"),
        ImageVo("9", "test3", "ic_9"),
        ImageVo("10", "test3", "ic_10"),
        ImageVo("11", "test3", "ic_11"),
        ImageVo("12", "test3", "ic_1"),
        ImageVo("13", "test3", "ic_2"),
        ImageVo("14", "test3", "ic_3"),
        ImageVo("15", "test3", "ic_4")
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