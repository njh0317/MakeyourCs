package com.example.makeyourcs.data.Repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.data.firebase.FirebaseAuthSource
import com.google.firebase.storage.FirebaseStorage

class PostRepository (
    private val firestore: FirebaseAuthSource
) {
    fun currentUser() = firestore.currentUser()

    fun observePostData(): MutableLiveData<PostClass>
    {
        firestore.observePostData()
        var data = firestore.postDataLiveData
        return data
    }
}