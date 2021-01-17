package com.example.makeyourcs.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.ValueObject
import com.example.makeyourcs.adapter.RecyclerFeedAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){
    private var userList = arrayListOf<ValueObject>(
        ValueObject("사진1", "test3", "ic_1"),
        ValueObject("사진2", "test3", "ic_2"),
        ValueObject("사잔3", "test3", "ic_3"),
        ValueObject("사진4", "test3", "ic_4"),
        ValueObject("사진5", "test3", "ic_5"),
        ValueObject("사진6", "test3",  "ic_6"),
        ValueObject("사진7", "test3",  "ic_7"),
        ValueObject("사진8", "test3",  "ic_8"),
        ValueObject("사진9", "test3", "ic_9"),
        ValueObject("사진10", "test3",  "ic_1"),
        ValueObject("사진11", "test3",  "ic_5"),
        ValueObject("사진12", "test3",  "ic_2"),
        ValueObject("사진13", "test3",  "ic_3"),
        ValueObject("사진14", "test3",  "ic_4"),
        ValueObject("사진15", "test3",  "ic_9") )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)
        Log.d("debug","it's in home fragment before setting adapter, layoutmanger")

//        //Set the adapter
//        val mAdapter = RecyclerFeedAdapter(view.context, userList)
//        recycler_view.adapter = mAdapter
//
//        //Set the LayoutManager
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
//        recycler_view.layoutManager = staggeredGridLayoutManager

        val mAdapter=RecyclerFeedAdapter(view.context,userList)
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        val rview=view.findViewById<RecyclerView>(R.id.recycler_view)
        rview.adapter=mAdapter
        rview.layoutManager=lmanager


        Log.d("debug","it's in home fragment after setting adapter, layoutmanger")

        return view;
    }
}