package com.example.makeyourcs.ui.user.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class UserMgtViewModelFactory (
    private val repository: AccountRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserMgtViewModel(repository) as T
    }
}