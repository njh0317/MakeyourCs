package com.example.makeyourcs.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    val TAG = "HOMEACTIVITY"
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.getUserData()
        viewModel.getAccountData()
//        updateUI()
        viewModel.userData.observe(this, Observer {
            binding.etUsername.text = it.userId
            binding.etEmail.text = it.email
            binding.subcount.text = it.sub_count.toString()
            binding.followingNum.text = it.following_num.toString()
            Log.d(TAG, it.toString())
        })
    }
    public override fun onStart() {
        super.onStart()
    }


}