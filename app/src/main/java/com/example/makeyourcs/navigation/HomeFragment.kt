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
        ValueObject("1", "test3", "ic_1"),
        ValueObject("2", "test3", "ic_2"),
        ValueObject("3", "test3", "ic_3"),
        ValueObject("4", "test3", "ic_4"),
        ValueObject("5", "test3", "ic_5"),
        ValueObject("6", "test3",  "ic_6"),
        ValueObject("7", "test3",  "ic_7"),
        ValueObject("8", "test3",  "ic_8"),
        ValueObject("9", "test3", "ic_9"),
        ValueObject("10", "test3",  "ic_10"),
        ValueObject("11", "test3",  "ic_11"),
        ValueObject("12", "test3",  "ic_1"),
        ValueObject("13", "test3",  "ic_2"),
        ValueObject("14", "test3",  "ic_3"),
        ValueObject("15", "test3",  "ic_4") )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)
        Log.d("debug","it's in home fragment before setting adapter, layoutmanger")

        val mAdapter=RecyclerFeedAdapter(view.context,userList)
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        val rview=view.findViewById<RecyclerView>(R.id.recycler_view)
        rview.adapter=mAdapter
        rview.layoutManager=lmanager


        return view;
    }
}