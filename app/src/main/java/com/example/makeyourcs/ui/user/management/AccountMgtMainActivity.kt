package com.example.makeyourcs.ui.user.management

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityAccountMgtMainBinding
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_mgt_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AccountMgtMainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: UserMgtViewModelFactory by instance()

//    private lateinit var auth: FirebaseAuth
//    private lateinit var viewModel: UserMgtViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_account_mgt_main)

        val binding: ActivityAccountMgtMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_mgt_main)
//        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
//        binding.viewmodel = viewModel

        var list = ArrayList<AccountMgtItem>()
        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname1)))
        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname2)))
        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname3)))

        val adapter = AccountMgtRecyclerAdapter(list)

        binding.accountRecyclerView.adapter = adapter
//        account_recyclerView.adapter = adapter
    }

}