package com.example.makeyourcs

import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.Repository.AccountRepository

class LoginViewModel(
    private val repository: AccountRepository
) : ViewModel(){
    var userId:String? =null
    var password:String? = null

    fun login(){
        if(userId.isNullOrEmpty()||password.isNullOrEmpty()){
            return
        }
        val disposable = repository.login(userId!!, password!!)


    }

}