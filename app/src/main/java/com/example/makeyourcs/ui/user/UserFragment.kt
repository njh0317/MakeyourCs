package com.example.makeyourcs.ui.user

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentHomeBinding
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.RecyclerFeedAdapter
import com.example.makeyourcs.ui.home.HomeViewModel
import com.example.makeyourcs.ui.home.HomeViewModelFactory
import com.example.makeyourcs.ui.home.InjectionFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton


class UserFragment : InjectionFragment(){

    private val factory : UserViewModelFactory by instance()
    lateinit var binding: FragmentUserBinding
    lateinit var viewmodel:UserViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user, container, false)
        var view=binding.root
        viewmodel= ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        binding.viewmodel=viewmodel

        //recyclerview adapter, layoutmanger setting
        val adapter= RecyclerFeedAdapter(view.context, viewmodel._imgList)
        binding.userRecycler.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.userRecycler.layoutManager=lmanager

        viewmodel.getUserData()

//        viewmodel.userData.observe(this, Observer {
//            //binding.profileName.text
//            binding.profileUsername = it.userId
//            binding.subcount.text = it.sub_count.toString()
//            binding.followingNum.text = it.following_num.toString()
//
//            Log.d(TAG, it.toString())
//        })


        return view

    }
}