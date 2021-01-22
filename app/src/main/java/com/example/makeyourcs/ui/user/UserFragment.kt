package com.example.makeyourcs.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentHomeBinding
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.RecyclerFeedAdapter

class UserFragment : Fragment(){
    lateinit var binding: FragmentUserBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user, container, false)
        var view=binding.root
        //   viewmodel=ViewModelProviders.of(this).get(HomeViewModel::class.java)

        var userList = arrayListOf<ImageVo>(
            ImageVo("1",  R.drawable.ic_1,"55"),
            ImageVo("2",  R.drawable.ic_2,"55"),
            ImageVo("3", R.drawable.ic_3,"55"),
            ImageVo("4",  R.drawable.ic_4,"55"),
            ImageVo("5",  R.drawable.ic_5,"55"),
            ImageVo("6",  R.drawable.ic_6,"55"),
            ImageVo("7",  R.drawable.ic_7,"55"),
            ImageVo("8",  R.drawable.ic_8,"55"),
            ImageVo("9",  R.drawable.ic_9,"55"),
            ImageVo("10",  R.drawable.ic_10,"55")
        )

        //recyclerview adapter, layoutmanger setting
        val adapter= RecyclerFeedAdapter(userList)
        binding.userRecycler.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.userRecycler.layoutManager=lmanager

        return view
    }
}