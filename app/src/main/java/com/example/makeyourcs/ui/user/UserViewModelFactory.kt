package com.example.makeyourcs.ui.user


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.home.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.TT
import org.kodein.di.direct


@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val repository: AccountRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }

}