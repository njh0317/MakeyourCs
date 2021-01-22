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
   // lateinit var viewmodel:HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //var view=LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        var view=binding.root
        //   viewmodel=ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val list = ArrayList<ImageVo>()
        list.add(ImageVo("1", ContextCompat.getDrawable(context!!,R.drawable.ic_1)!!,"60"))
        list.add(ImageVo("2", ContextCompat.getDrawable(context!!,R.drawable.ic_2)!!,"61"))
        list.add(ImageVo("3", ContextCompat.getDrawable(context!!,R.drawable.ic_3)!!,"62"))
        list.add(ImageVo("4", ContextCompat.getDrawable(context!!,R.drawable.ic_4)!!,"63"))
        list.add(ImageVo("5", ContextCompat.getDrawable(context!!,R.drawable.ic_5)!!,"64"))
        list.add(ImageVo("6", ContextCompat.getDrawable(context!!,R.drawable.ic_6)!!,"65"))
        list.add(ImageVo("7", ContextCompat.getDrawable(context!!,R.drawable.ic_7)!!,"66"))
        list.add(ImageVo("8", ContextCompat.getDrawable(context!!,R.drawable.ic_8)!!,"67"))
        list.add(ImageVo("9", ContextCompat.getDrawable(context!!,R.drawable.ic_9)!!,"68"))
        list.add(ImageVo("10", ContextCompat.getDrawable(context!!,R.drawable.ic_10)!!,"69"))

        //recyclerview adapter, layoutmanger setting
        val adapter= RecyclerFeedAdapter(list)
        binding.recyclerView.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager=lmanager

        return view
    }
}