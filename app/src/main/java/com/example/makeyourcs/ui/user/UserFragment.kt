package com.example.makeyourcs.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.Groupname
import com.example.makeyourcs.ui.RecyclerFeedAdapter
import com.example.makeyourcs.ui.home.InjectionFragment
import com.example.makeyourcs.ui.user.management.change.ChangeAccountMainActivity
import com.example.makeyourcs.ui.user.settings.SettingDialogFragment
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance


class UserFragment : InjectionFragment(){

    private val factory : UserViewModelFactory by instance()
    lateinit var binding: FragmentUserBinding
    lateinit var viewmodel:UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        var view=binding.root
        viewmodel= ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        binding.viewmodel=viewmodel

        //recyclerview adapter, layoutmanger setting
        val adapter= RecyclerFeedAdapter(view.context, viewmodel._imgList)
        binding.userRecycler.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.userRecycler.layoutManager=lmanager

        //observer
        viewmodel.getUserData()
        viewmodel.userData.observe(viewLifecycleOwner, Observer {
            binding.profileUsername.text = "@" + it.userId
            binding.profileFollowing.text = "팔로잉 " + it.following_num
        })

        viewmodel.getAccountData(Groupname.groupname)
        Log.d("groupname", "groupname in user: " + Groupname.groupname)

        viewmodel.accountData.observe(viewLifecycleOwner, Observer {
            binding.profileName.text = it.name
            binding.profileIntro.text = it.introduction
            binding.profileFollower.text = "팔로워 " + it.follower_num.toString()

            lifecycleScope.launch {
                val uri = viewmodel.getProfileImageurl(it.profile_pic_name!!)
                Log.d("uri", "uri : " + uri)
                if (uri != null) {
                    Glide.with(view.context)
                        .load(uri)
                        .circleCrop()
                        .into(binding.profileImage)
                }
            }
        })

        viewmodel.getPostData()
//        viewmodel.postData.observe(viewLifecycleOwner, Observer {
//
//        })
//

        //setting button listener
        binding.setting.setOnClickListener {
            showSettingDialog()
        }
        view.change_sub.setOnClickListener {
            showChangeFragment()
        }
        Log.i("UserFragment", "onCreateView() call")
        return view
    }

    override fun onResume() {

        Log.i("UserFragment", "onResume() call")
        viewmodel.getAccountData(Groupname.groupname)
        super.onResume()
    }
//    override fun onStart() {
//        super.onStart()
//        Log.i("UserFragment", "onStart() call")
//    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

    private fun showChangeFragment() {
        val intent = Intent(context, ChangeAccountMainActivity::class.java)
        startActivity(intent)
    }
    private fun showSettingDialog(){
        val dialog =
            SettingDialogFragment(context)
        dialog.show(childFragmentManager, null)
    }
}