package com.example.makeyourcs.data.Repository

import com.example.makeyourcs.data.firebase.FirebaseSource

class AccountRepository(
    private val firebase:FirebaseSource
) {
    fun confirmID(userId : String) = firebase.confirmID(userId) //아이디 존재 여부

    fun login(userId : String, userPw : String) = firebase.confirmID(userId, userPw) //로그인 가능 여부
}