package com.example.makeyourcs.ui.user.management.follower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySelectFollowerForNewAccBinding
import com.example.makeyourcs.ui.user.management.UserMgtViewModel
import com.example.makeyourcs.ui.user.management.UserMgtViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SelectFollowerForNewAccActivity : AppCompatActivity(), KodeinAware, FollowerRecyclerAdapter.OnItemClickListener {
    override val kodein by kodein()
    private val factory: UserMgtViewModelFactory by instance()
    lateinit var viewModel: UserMgtViewModel
    lateinit var binding: ActivitySelectFollowerForNewAccBinding
    lateinit var adapter: FollowerRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_select_follower_for_new_acc)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_follower_for_new_acc)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel

//        var list = arrayListOf<FollowerItem>(FollowerItem(R.drawable.ic_account, "lululalyang"),
//            FollowerItem(R.drawable.ic_account, "na_j222"),
//            FollowerItem(R.drawable.ic_account, "ekdbsl"),
//            FollowerItem(R.drawable.ic_account, "eunj2"),
//            FollowerItem(R.drawable.ic_account, "iamkimsobin"))

        viewModel.getFollowerData()
        viewModel.followerData.observe(this, Observer{
            adapter = FollowerRecyclerAdapter(viewModel.getFollowerItemList(), this)
            binding.followerRecyclerView.adapter = adapter
        })

//        adapter = FollowerRecyclerAdapter(list)
//        binding.followerRecyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int, view: View) {
        var followerEmail = viewModel.getFollowerItemList().get(position).id
        Log.d("inFollowerMainActivity", "$followerEmail 추가")

    }
}