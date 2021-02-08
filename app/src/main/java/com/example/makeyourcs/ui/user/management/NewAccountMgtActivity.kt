package com.example.makeyourcs.ui.user.management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityNewAccountMgtBinding
import com.squareup.okhttp.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NewAccountMgtActivity : AppCompatActivity() , KodeinAware {
    override val kodein by kodein()
    private val factory : UserMgtViewModelFactory by instance()
    private lateinit var viewModel: UserMgtViewModel
    private var backBtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account_mgt)

        val binding: ActivityNewAccountMgtBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_account_mgt)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.getUserData()
        viewModel.getAccountData()

        viewModel.userData.observe(this, Observer {
            binding.userName.text = "@" + it.userId
            Log.d("NEWACCOUNT", it.toString())
        })

        binding.cancelBtn.setOnClickListener{
            super.onBackPressed()
        }
    }

    public override fun onStart(){
        super.onStart()
    }
}