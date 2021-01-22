package com.example.makeyourcs.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentHomeBinding
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.RecyclerFeedAdapter
import javax.crypto.spec.PSource

class HomeFragment : Fragment(){
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
    lateinit var binding:FragmentHomeBinding
    lateinit var viewmodel:HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //var view=LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        viewmodel=ViewModelProviders.of(this).get(HomeViewModel::class.java)
        var view=binding.root

        //recyclerview adapter, layoutmanger setting

        val mAdapter= RecyclerFeedAdapter(view.context, userList)
        binding.recyclerView.adapter=mAdapter

        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager=lmanager


        return view
       // return bind.root;
    }
}