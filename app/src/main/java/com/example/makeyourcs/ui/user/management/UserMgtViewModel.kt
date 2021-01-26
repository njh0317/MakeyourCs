package com.example.makeyourcs.ui.user.management

import android.accounts.Account
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.ui.auth.SignupActivity
import com.example.makeyourcs.utils.startAccountMgtMainActivity

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

//    init{
////        var initlist = ArrayList<AccountMgtItem>()
//        var data = repository.observeAccountData()
//        var account = data.value?.iterator()
//
//        if(account != null){
//            while(account.hasNext()){
////                Log.d("account","in Account")
//                list.add(AccountMgtItem(R.drawable.profile_oval, account.next().name.toString()))
////                list.add(AccountMgtItem((R.drawable.profile_oval)!!, account.next().name.toString()))
//            }
//        }
//        liveData.postValue(list)
//    }

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
        System.out.println("account Data: " + data.value)
        _accountData = data
    }

    fun getAccountList(): MutableLiveData<List<AccountClass.SubClass>> { // accountmgtmain activity는 이 메소드를 observe
        var data = repository.observeAccountData()
        return data
    }

    fun getItemList(): ArrayList<AccountMgtItem>{ // 위 메소드로 전달받은 값이 바뀌면 이 메소드 호출 -> recyclerItemList를 리턴
        var itemlist = ArrayList<AccountMgtItem>()
        var data = repository.observeAccountData()
        //data.value?.sortedBy { it.sub_num }

        var account = data.value?.iterator()
        if(account != null){
            while(account.hasNext()){
                Log.d("account","in Account")
                itemlist.add(AccountMgtItem(R.drawable.profile_oval, account.next().name.toString()))
            }
        }
        return itemlist
    }

    fun AddNewAccount(view: View){
        System.out.println("new subAccount!!")
        var data = repository.observeUserData()
        repository.setSubAccount(data.value?.sub_count!!, subName.get().toString(), groupName.get().toString(), subIntroduce.get().toString(), "default")
        view.context.startAccountMgtMainActivity()
    }


    fun goToAddNewAccount(view: View) {
        Intent(view.context, NewAccountMgtActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}