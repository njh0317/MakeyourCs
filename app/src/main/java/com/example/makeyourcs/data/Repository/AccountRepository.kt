package com.example.makeyourcs.data.Repository

import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.firebase.FirebaseAuthSource

class AccountRepository(
    private val firebase:FirebaseAuthSource
) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(account: AccountClass) = firebase.register(account)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}