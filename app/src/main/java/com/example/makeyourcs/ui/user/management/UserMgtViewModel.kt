package com.example.makeyourcs.ui.user.management

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.ui.auth.SignupActivity

class UserMgtViewModel (
    private val repository: AccountRepository
): ViewModel(){
    val TAG = "HOMEVIEWMODEL"
    private var _userData = MutableLiveData<AccountClass>()
    val userData: LiveData<AccountClass>
        get()= _userData
    private var _accountData = MutableLiveData<List<AccountClass.SubClass>>()
    val accountData: LiveData<List<AccountClass.SubClass>>
        get()= _accountData

    var email: String? = null
    var id: String? = null
    var sub_count: Int? = null
    var following_num: Int? = null

    //sub account Info
    var subName = ObservableField<String>()
    var groupName = ObservableField<String>()
    var subIntroduce = ObservableField<String>()

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
        System.out.println("getAccountData "+data.value)
        _accountData = data
    }

    fun setNewAccount(view: View){
        repository.setSubAccount(sub_count!!, subName.get().toString(), groupName.get().toString(), subIntroduce.get().toString(), "default")
        System.out.println("new subAccount!!")
        Log.d("UserMgtViewModel", "new Account!!")
    }

}