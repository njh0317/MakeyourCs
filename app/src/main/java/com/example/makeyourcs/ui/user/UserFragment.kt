package com.example.makeyourcs.ui.user

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.RecyclerFeedAdapter
import com.example.makeyourcs.ui.home.InjectionFragment
import kotlinx.android.synthetic.main.fragment_settingdlg.*
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

        viewmodel.getAccountData()
        viewmodel.accountData.observe(viewLifecycleOwner, Observer {
            binding.profileName.text=it[0].name
            binding.profileIntro.text=it[0].introduction
            binding.profileFollower.text="팔로워 " + it[0].follower_num.toString()
        })

        //setting button listener
        binding.setting.setOnClickListener {
            showDialog()

        }
        return view
    }

    private fun showDialog(){
        val dialog = SettingDialogFragment()
        dialog.show(childFragmentManager, null)
    }
}