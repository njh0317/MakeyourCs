package com.example.makeyourcs.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
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

    private val sharedViewModel: UserViewModel by viewModels(ownerProducer = { this })


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

        binding.setting.setOnClickListener { view ->
            showDialog()
            //val intent = Intent(context, DetailedActivity::class.java)
            //startActivity(intent) // start Intent
        }
        return view
    }

    private fun showDialog(){
        val dialog = SettingDialogFragment()
        dialog.show(childFragmentManager, "SettingDialogFragment")
    }


}