package com.example.makeyourcs.data.firebase

import android.accounts.Account
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FirebaseSource {
    private val firestore = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    val TAG = "FirebaseSource"
    val userDataLiveData = MutableLiveData<AccountClass>()

    fun confirmID(userId:String) : Boolean
    {
        val docRef = firestore.collection("Account").document(userId)
        var flag:Boolean = false
        docRef.get()
            .addOnSuccessListener { document ->
                flag = document != null
            }
            .addOnFailureListener{ exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return flag

    }
    fun observeUserData(userId: String) {
        try {
            firestore.collection("Account").document(userId).addSnapshotListener{ documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                firebaseFirestoreException?.let {
                    Log.e(TAG, firebaseFirestoreException.toString())
                    return@addSnapshotListener
                }

                val data = documentSnapshot?.toObject(AccountClass::class.java)

                data?.let {
                    Log.d(TAG, "post new value")
                    userDataLiveData.postValue(data)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }


}