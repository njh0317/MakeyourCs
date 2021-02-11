package com.example.makeyourcs.ui.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.user.management.UserMgtViewModel

@Suppress("UNCHECKED_CAST")
class UploadViewModelFactory (
    private val repository: AccountRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UploadViewModel(repository) as T
    }
}