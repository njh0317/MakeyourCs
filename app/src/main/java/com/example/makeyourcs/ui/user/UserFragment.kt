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
        binding.userRecycler.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.userRecycler.layoutManager=lmanager

        return view
    }
}