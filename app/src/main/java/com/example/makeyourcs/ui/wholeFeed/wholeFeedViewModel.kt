package com.example.makeyourcs.ui.wholeFeed

import android.content.Intent
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.data.Repository.PostRepository
import kotlinx.android.synthetic.main.activity_storage.*


class wholeFeedViewModel (private val repository: AccountRepository): ViewModel() {
    var img = ObservableField<Uri>()

    val post by lazy{
        repository.currentUser()
    }
    fun showImg(){

    }
}