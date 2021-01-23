 package com.example.makeyourcs.ui.serach

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentSearchBinding
import com.example.makeyourcs.ui.home.HomeViewModel
import com.example.makeyourcs.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

 class SearchFragment(override val kodein: Kodein) : Fragment(), KodeinAware {

    private val factory : HomeViewModelFactory by instance()
    val TAG = "SEARCHFRAGMENT"
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentSearchBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        //binding.viewmodel = viewModel
        viewModel.getUserData()


//        updateUI()
        viewModel.userData.observe(this, Observer {
            binding.etUsername.text = it.userId
            binding.etEmail.text = it.email
            binding.subcount.text = it.sub_count.toString()
            binding.followingNum.text = it.following_num.toString()

            Log.d(TAG, it.toString())
        })

        return view
    }
     public override fun onStart() {
         super.onStart()
     }


 }