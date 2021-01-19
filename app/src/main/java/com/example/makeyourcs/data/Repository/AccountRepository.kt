package com.example.makeyourcs.data.Repository

import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.firebase.FirebaseAuthSource
import com.example.makeyourcs.data.firebase.FirebaseSource

class AccountRepository(
    private val firestore: FirebaseAuthSource
) {
    fun login(email: String, password: String) = firestore.login(email, password)

    fun register(account: AccountClass) = firestore.register(account)

    fun currentUser() = firestore.currentUser()

    fun logout() = firestore.logout()
    fun observeUserData2(): MutableLiveData<AccountClass>
    {
        firestore.observeUserData2()
        var data = firestore.userDataLiveData
        return data
    }
}