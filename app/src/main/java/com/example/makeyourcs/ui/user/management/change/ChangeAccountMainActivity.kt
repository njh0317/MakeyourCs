package com.example.makeyourcs.ui.user.management.change

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
import com.example.makeyourcs.databinding.ActivityChangeAccountMainBinding
import com.example.makeyourcs.ui.Groupname
import com.example.makeyourcs.ui.MainActivity
import com.example.makeyourcs.ui.user.management.AccountMgtRecyclerAdapter
import com.example.makeyourcs.ui.user.management.UserMgtViewModel
import com.example.makeyourcs.ui.user.management.UserMgtViewModelFactory
import com.example.makeyourcs.ui.user.management.dialog.ChangeSubAccountDialogFragment
import com.example.makeyourcs.ui.user.management.dialog.DelSubAccountDialogFragment
import com.example.makeyourcs.ui.user.management.dialog.OriginCannotDeleteDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ChangeAccountMainActivity : AppCompatActivity(), KodeinAware, ChangeAccountRecyclerAdapter.OnItemClickListener{
    override val kodein by kodein()
    private val factory: UserMgtViewModelFactory by instance()
    lateinit var viewModel: UserMgtViewModel
    lateinit var binding: ActivityChangeAccountMainBinding
    lateinit var adapter: ChangeAccountRecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_account_mgt_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_account_main)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel

        binding.backButton.setOnClickListener {
            super.onBackPressed()
        }

        viewModel.getAccountData()
        viewModel.accountData.observe(this, Observer {
            adapter = ChangeAccountRecyclerAdapter(viewModel.getItemList(), this)
            binding.accountRecyclerView.adapter = adapter
        })
    }

    override fun onItemClick(position: Int) {
        var groupname = viewModel.getItemList().get(position).groupname

        val dialog = ChangeSubAccountDialogFragment(this)
        dialog.setButtonClickListener(object: ChangeSubAccountDialogFragment.OnButtonClickListener{
            override fun onAccountChangeButtonClicked() {
                Groupname.groupname = groupname
                onBackPressed()
            }
        })
        dialog.show(supportFragmentManager, "changeSubAccountDialogFragment")
    }

    public override fun onStart() {
        super.onStart()
    }
}