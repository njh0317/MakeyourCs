package com.example.makeyourcs.ui.upload


import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.utils.startLoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class UploadViewModel(
    private val repository: AccountRepository
) :ViewModel() {
    val TAG = "UPLOADVIEWMODEL"



    val user by lazy {
        repository.currentUser()
    }

    fun sharePost(){
        Log.d("upload","upload click listener")
        //TODO: intent 전환
    }

}
