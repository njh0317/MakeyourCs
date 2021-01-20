package com.example.makeyourcs.data.Repository

import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.firebase.FirebaseAuthSource

class AccountRepository(
    private val firestore: FirebaseAuthSource
) {
    fun login(email: String, password: String) = firestore.login(email, password)

    fun register(account: AccountClass) = firestore.register(account)

    fun currentUser() = firestore.currentUser()

    fun logout() = firestore.logout()

    fun observeUserData(): MutableLiveData<AccountClass> // 계정 정보 가져오기
    {
        firestore.observeUserData()
        var data = firestore.userDataLiveData
        return data
    }

    fun setOriginAccount(name:String, introduction:String, imageurl:String) //본캐 정보 저장
        = firestore.setOriginAccount(name, introduction, imageurl)

    fun setSubAccount(subaccount_num:Int, name:String, group_name:String, introduction:String, imageurl:String) //부 정보 저장
            = firestore.setSubAccount(subaccount_num, name, group_name, introduction, imageurl)



}