package com.example.makeyourcs.ui.home

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.utils.startLoginActivity


class HomeViewModel(

    private val repository: AccountRepository
) : ViewModel() {
    val TAG = "HOMEVIEWMODEL"
    private var _userData = MutableLiveData<AccountClass>()
    val userData:LiveData<AccountClass>
        get()= _userData
    private var _accountData = MutableLiveData<List<AccountClass.SubClass>>()
    val accountData:LiveData<List<AccountClass.SubClass>>
        get()= _accountData

    var email: String? = null
    var id: String? = null
    var sub_count: Int? = null
    var following_num: Int? = null
    //auth listener
    var authListener: AuthListener? = null

    val user by lazy {
        repository.currentUser()
    }

    fun getUserData() {
        System.out.println("getUserData")
        var data = repository.observeUserData()
        System.out.println("getUserData"+data.value)
        _userData = data
    }

    fun getAccountData() {
        System.out.println("getAccountData")
        var data = repository.observeAccountData()
        System.out.println("getAccountData"+data.value)
        _accountData = data
    }

    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }


}