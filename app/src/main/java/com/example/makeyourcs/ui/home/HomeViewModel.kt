package com.example.makeyourcs.ui.home

import android.view.View
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
        var data = repository.observeUserData2()
        System.out.println("getUserData"+data.value)
        _userData = data
    }

    fun updateUI(){
    }

    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }


}