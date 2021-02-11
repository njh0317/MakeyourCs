package com.example.makeyourcs.ui.user.management.follower

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySelectFollowerForNewAccBinding
import com.example.makeyourcs.ui.user.management.UserMgtViewModel
import com.example.makeyourcs.ui.user.management.UserMgtViewModelFactory
import kotlinx.android.synthetic.main.followerlist_item.view.*
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

    override fun onItemClick(position: Int, view: View, SelectedItems: SparseBooleanArray) {
        var followerEmail = viewModel.getFollowerItemList().get(position).id

        if(SelectedItems.get(position, false)){
            SelectedItems.put(position, false)
            view.plusbtn.setBackgroundResource(R.drawable.orangebtn)
            view.plusbtn.setText("추가")
            view.plusbtn.setTextColor(Color.WHITE)
            Log.d("inFollowerMainActivity", "$followerEmail 취소됨")
        }else{
            SelectedItems.put(position, true)
            view.plusbtn.setBackgroundResource(R.drawable.whitebtn)
            view.plusbtn.setText("취소")
            view.plusbtn.setTextColor(Color.BLACK)
            Log.d("inFollowerMainActivity", "$followerEmail 추가됨")
        }




    }
}