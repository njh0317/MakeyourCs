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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_account_mgt_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_mgt_main)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel

        binding.backButton.setOnClickListener {
            super.onBackPressed()
        }

        viewModel.getAccountData()
        viewModel.accountData.observe(this, Observer {
            adapter = AccountMgtRecyclerAdapter(viewModel.getItemList(), this)
            binding.accountRecyclerView.adapter = adapter
        })

        Log.d("ACCOUNTMGTMAIN", "in func")
    }

    override fun onItemClick(position: Int) {
//        var groupname = viewModel.getItemList().get(position).groupname
//        Toast.makeText(this, "Item $groupname clicked", Toast.LENGTH_SHORT).show()
//        System.out.println("Clicked!! $position")
    }

    override fun onLongClick(position: Int) {
        var groupname = viewModel.getItemList().get(position).groupname
//        Log.d("onLongClick", "$groupname long clicked")

        val dialog = AccMgtDialogFragment(this)
        // TODO: 계정관리 다이얼로그 띄운 후에 삭제 다이얼로그 띄우기
        // 여기부터 부캐 삭제 다이얼로그
//        val dialog = DelSubAccountDialogFragment(this)
//        // 버튼 클릭 이벤트 설정
//        dialog.setButtonClickListener(object: DelSubAccountDialogFragment.OnButtonClickListener{
//            override fun onAccountDeleteButtonClicked() {
////                Log.d("MgtMainActivity", "delete btn clicked")
//                viewModel.DeleteAccount(groupname)
//            }
//        })
//
//        if(position != 0) {
//            dialog.show(supportFragmentManager, "DelSubAccountDialogFragment")
//        }
//        else {
////            Toast.makeText(this, "본 계정은 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show()
//            val dialog2 = OriginCannotDeleteDialog(this)
//            dialog2.WarningConfirm()
//        }
        //여기까지
    }


    public override fun onStart() {
        super.onStart()
    }
}