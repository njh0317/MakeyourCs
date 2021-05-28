package com.example.makeyourcs.ui.user.management.follower

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Toast
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
//,
//FollowerRecyclerAdapter.OnItemClickListener
class SelectFollowerForNewAccActivity : AppCompatActivity(), KodeinAware{
    override val kodein by kodein()
    private val factory: UserMgtViewModelFactory by instance()
    lateinit var viewModel: UserMgtViewModel
    lateinit var binding: ActivitySelectFollowerForNewAccBinding
    lateinit var adapter: FollowerRecyclerAdapter
//    private var SelectedItems = SparseBooleanArray(0)

    private lateinit var groupname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra("groupname")) {
            groupname = intent.getStringExtra("groupname")
        } else {
            Toast.makeText(this, "groupname Error!", Toast.LENGTH_SHORT).show()
        }

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_select_follower_for_new_acc)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.getFollowerData()
        viewModel.followerData.observe(this, Observer {
            adapter = FollowerRecyclerAdapter(viewModel.getFollowerItemList())
            Log.e("adapter", adapter.toString())
            binding.followerRecyclerView.adapter = adapter
        })

        binding.selectComplete.setOnClickListener {
            selectedFollower()
        }
    }

    fun selectedFollower() {
        var followerlist = viewModel.getFollowerItemList()
        var selectedfollower = mutableListOf<String>()

        var SelectedItems = adapter.SelectedItems

        Log.e("selected item", SelectedItems.toString())

        for (i in 0..(followerlist.size - 1)) {
            if (SelectedItems.get(i)) {
                selectedfollower.add(followerlist.get(i).id)
            }
        }
        viewModel.SetFollowertoSubaccount(this, groupname, selectedfollower)
    }

//    override fun onItemClick(position: Int, view: View) {
//        var followerEmail = viewModel.getFollowerItemList().get(position).id
//
//        if (SelectedItems.get(position, false)) {
//            SelectedItems.put(position, false)
//            view.plusbtn.setBackgroundResource(R.drawable.orangebtn)
//            view.plusbtn.setText("추가")
//            view.plusbtn.setTextColor(Color.WHITE)
//            Log.d("inFollowerMainActivity", "$followerEmail 취소됨")
//        } else {
//            SelectedItems.put(position, true)
//            view.plusbtn.setBackgroundResource(R.drawable.whitebtn)
//            view.plusbtn.setText("취소")
//            view.plusbtn.setTextColor(Color.BLACK)
//            Log.d("inFollowerMainActivity", "$followerEmail 추가됨")
//        }
//    }
}