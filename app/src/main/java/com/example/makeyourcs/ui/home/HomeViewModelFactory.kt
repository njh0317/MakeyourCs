package com.example.makeyourcs.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.makeyourcs.data.Repository.AccountRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: AccountRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }

}