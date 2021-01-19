package com.example.makeyourcs.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.data.firebase.FirebaseSource
import com.example.makeyourcs.utils.startLoginActivity
import com.google.firebase.firestore.EventListener

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class HomeViewModel(

    private val repository: AccountRepository
) : ViewModel() {
    val TAG = "HOMEVIEWMODEL"
    private var _userData = MutableLiveData<AccountClass>()
    val userData:LiveData<AccountClass> = _userData
    val user by lazy {
        repository.currentUser()
    }

    fun getUserData() {
        System.out.println("getUserData")
        var data = repository.observeUserData2()
        System.out.println("getUserData"+data.value)
        _userData = data
    }

    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }


}