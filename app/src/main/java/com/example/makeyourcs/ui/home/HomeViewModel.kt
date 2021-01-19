package com.example.makeyourcs.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.utils.startLoginActivity

class HomeViewModel(
    private val repository: AccountRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }
}