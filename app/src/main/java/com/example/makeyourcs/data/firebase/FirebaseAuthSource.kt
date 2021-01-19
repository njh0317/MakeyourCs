package com.example.makeyourcs.data.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import io.reactivex.Completable

class FirebaseAuthSource {
    val TAG = "FirebaseSource"
    val userDataLiveData = MutableLiveData<AccountClass>()
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }


    fun login(email: String, password: String) = Completable.create { emitter ->
        Log.d("TAG", email)
        firebaseAuth.signInWithEmailAndPassword(email!!, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
//                    System.out.println("login : " + firebaseAuth.currentUser?.email)
                    emitter.onComplete()
                }
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(account: AccountClass) = Completable.create { emitter ->
        System.out.println(account)
        //https://inspirecoding.app/lessons/using-viewmodel/
        firestore.collection("Account").document(account.email.toString()).set(account)

        firebaseAuth.createUserWithEmailAndPassword(account.email.toString(), account.pw.toString()).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    System.out.println("insert success")
                    emitter.onComplete()
                }
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    fun deleteUser() {
        // [START delete_user]
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
        // [END delete_user]
    }
    fun confirmID(userId:String, userPw:String) = Completable.create{ emitter ->
        val docRef = firestore.collection("Account").document(userId)
        docRef.get().addOnCompleteListener{
            if (!emitter.isDisposed) {
                if (it.isSuccessful){
                    if (it != null) {
                        emitter.onComplete()
                    }
                    else{
                        //TODO:emitter 에 어떤 메시지 보내야하는지..
                    }
                }
                else
                    emitter.onError(it.exception!!)
            }
        }

    }

    fun observeUserData(userId: String) {
        System.out.println("observeUserData")
        try {
            firestore.collection("Account").document(userId).addSnapshotListener{ documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                firebaseFirestoreException?.let {
                    Log.e(TAG, firebaseFirestoreException.toString())
                    return@addSnapshotListener
                }

                val data = documentSnapshot?.toObject(AccountClass::class.java)

                data?.let {
                    Log.d(TAG, "post new value")
                    System.out.println(data)
                    userDataLiveData.postValue(data)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }
    fun observeUserData2() {
        System.out.println("observeUserData")
        System.out.println("observeUserData2: " + currentUser()!!.email)

        try {
            firestore.collection("Account").whereEqualTo("email", currentUser()!!.email.toString()).addSnapshotListener{ value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    doc?.let {
                        val data = it?.toObject(AccountClass::class.java)
                        System.out.println(data)
                        userDataLiveData.postValue(data)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }


}