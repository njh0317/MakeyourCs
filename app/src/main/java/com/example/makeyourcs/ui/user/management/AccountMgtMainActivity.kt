package com.example.makeyourcs.ui.user.management

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityAccountMgtMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AccountMgtMainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: UserMgtViewModelFactory by instance()
    lateinit var viewModel: UserMgtViewModel
    lateinit var binding: ActivityAccountMgtMainBinding
    lateinit var adapter: AccountMgtRecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_mgt_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_mgt_main)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.getAccountList().observe(this, Observer{
            Log.d("GETACCOUNTLIST", "change!!")
            var newAdapter = AccountMgtRecyclerAdapter(viewModel.getItemList())
            binding.accountRecyclerView.adapter = newAdapter
        })

        Log.d("ACCOUNTMGTMAIN", "in func")

    }

    public override fun onStart() {
        super.onStart()
    }
}