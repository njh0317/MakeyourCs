package com.example.makeyourcs.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.MainActivity
import com.example.makeyourcs.ui.RecyclerFeedAdapter
import com.example.makeyourcs.ui.home.InjectionFragment
import com.example.makeyourcs.ui.user.settings.SettingDialogFragment
import io.reactivex.annotations.SchedulerSupport.IO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.generic.instance


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

        //observer
        viewmodel.getUserData()
        viewmodel.userData.observe(viewLifecycleOwner, Observer {
            binding.profileUsername.text="@"+it.userId
            binding.profileFollowing.text="팔로잉 "+it.following_num.toString()
        })

        viewmodel.getAccountData((activity as MainActivity).groupname!!)
        Log.d("groupname","groupname in user: "+(activity as MainActivity).groupname)

        viewmodel.accountData.observe(viewLifecycleOwner, Observer {
            binding.profileName.text=it.name
            binding.profileIntro.text=it.introduction
            binding.profileFollower.text="팔로워 " + it.follower_num.toString()

            lifecycleScope.launch {
                val uri = viewmodel.getImageurl(it.profile_pic_name!!)
                Log.d("uri","uri : "+uri)
                if(uri!=null){
                    Glide.with(view.context)
                        .load(uri)
                        .circleCrop()
                        .into(binding.profileImage)
                }
            }
        })

        //setting button listener
        binding.setting.setOnClickListener {
            showSettingDialog()
        }
        return view
    }
    private fun showSettingDialog(){
        val dialog =
            SettingDialogFragment(context)
        dialog.show(childFragmentManager, null)
    }
}