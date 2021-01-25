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
    private lateinit var viewModel: UserMgtViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_mgt_main)

        val binding: ActivityAccountMgtMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_mgt_main)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.getUserData()
        viewModel.getAccountData()

//        val accounts = viewModel.accountData.value
//        val account = accounts?.iterator()
//        if (account != null) {
//            while(account.hasNext())
//                System.out.println("here!! " + account.next())
//            println("size: " + accounts.size)
////                System.out.println("in AccountMgtMainActivity : " + viewModel.accountData)
//        }
        var list = ArrayList<AccountMgtItem>()

        viewModel.userData.observe(this, Observer {
            list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, it.userId.toString()+" (본 계정)"))
        })

        viewModel.accountData.observe(this, Observer {
            list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, it.get(0).name.toString()))
        })

//        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname1)))
//        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname2)))
//        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname3)))

        val adapter = AccountMgtRecyclerAdapter(list)

        binding.accountRecyclerView.adapter = adapter
        Log.d("ACCOUNTMGTMAIN", "in func")
//        account_recyclerView.adapter = adapter
    }

    public override fun onStart() {
        super.onStart()
    }
}