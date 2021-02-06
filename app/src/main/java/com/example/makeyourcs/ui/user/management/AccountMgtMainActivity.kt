package com.example.makeyourcs.ui.user.management

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityAccountMgtMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AccountMgtMainActivity : AppCompatActivity(), KodeinAware, AccountMgtRecyclerAdapter.OnItemClickListener{
    override val kodein by kodein()
    private val factory: UserMgtViewModelFactory by instance()
    lateinit var viewModel: UserMgtViewModel
    lateinit var binding: ActivityAccountMgtMainBinding
    lateinit var adapter: AccountMgtRecyclerAdapter
    private var mBtn : Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_account_mgt_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_mgt_main)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel
        mBtn = findViewById(R.id.back_button) as Button
        mBtn!!.setOnClickListener(View.OnClickListener {
            if(it.id == R.id.back_button){
                super.onBackPressed()

            } })

        viewModel.getAccountData()
        viewModel.accountData.observe(this, Observer {
            adapter = AccountMgtRecyclerAdapter(viewModel.getItemList(), this)
            binding.accountRecyclerView.adapter = adapter
        })

        Log.d("ACCOUNTMGTMAIN", "in func")

    }
    override fun onItemClick(position: Int) {
        var groupname = viewModel.getItemList().get(position).groupname
        Toast.makeText(this, "Item $groupname clicked", Toast.LENGTH_SHORT).show()
        System.out.println("Clicked!! $position")
    }

    override fun onLongClick(position: Int) {
        var groupname = viewModel.getItemList().get(position).groupname
        val dialog = DelSubAccountDialogFragment(this, groupname)
        if(position != 0) {
            dialog.show(supportFragmentManager!!, "DelSubAccountDialogFragment")
        }
        else
            Toast.makeText(this, "본 계정은 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show()
    }


    public override fun onStart() {
        super.onStart()
    }
}