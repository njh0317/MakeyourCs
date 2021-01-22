package com.example.makeyourcs.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
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

    lateinit var binding:FragmentHomeBinding
    lateinit var viewmodel:HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //var view=LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        var view=binding.root
        viewmodel=ViewModelProviders.of(this).get(HomeViewModel::class.java)


        //recyclerview adapter, layoutmanger setting
        val adapter= RecyclerFeedAdapter(viewmodel._imgList)
        binding.recyclerView.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager=lmanager

        return view
    }
}