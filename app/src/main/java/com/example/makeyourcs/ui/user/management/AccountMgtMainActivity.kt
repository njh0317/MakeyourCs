package com.example.makeyourcs.ui.user.management

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityAccountMgtMainBinding
import kotlinx.android.synthetic.main.activity_account_mgt_main.*

class AccountMgtMainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_account_mgt_main)

        val binding: ActivityAccountMgtMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_mgt_main)

        var list = ArrayList<AccountMgtItem>()
        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname1)))
        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname2)))
        list.add(AccountMgtItem(getDrawable(R.drawable.profile_oval)!!, getString(R.string.accname3)))

        val adapter = AccountMgtRecyclerAdapter(list)

        binding.accountRecyclerView.adapter = adapter
//        account_recyclerView.adapter = adapter
    }
}