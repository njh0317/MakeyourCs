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
                    userIdbyEmail()
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
                    userIdbyEmail()
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
    fun userIdbyEmail() : String? {
        var userId:String? = null
        firestore.collection("Account")
            .whereEqualTo("email", currentUser()!!.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    userId = document.get("userId").toString()
                    Log.d(TAG, "userId get success "+userId)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return userId
    }

    fun observeUserData() {
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

    fun setOriginAccount(name:String, introduction:String, imageurl:String) { //TODO: 사진 url :default
        val OriginAccount = AccountClass.SubClass()
        OriginAccount.name = name
        OriginAccount.introduction = introduction
        OriginAccount.sub_num = 0
        OriginAccount.group_name = "default"
        OriginAccount.profile_pic_url = imageurl


        firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(OriginAccount.group_name.toString())
            .set(OriginAccount)
            .addOnSuccessListener {
                Log.d(TAG, "First subaccount insert complete")
                return@addOnSuccessListener
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e)
                    return@addOnFailureListener
            }
    }
    fun setSubAccount(subaccount_num:Int, name:String, group_name:String, introduction:String, imageurl:String) { //TODO: 사진 url :default
        val SubAccount = AccountClass.SubClass()
        SubAccount.name = name
        SubAccount.introduction = introduction
        SubAccount.sub_num = subaccount_num+1
        SubAccount.group_name = group_name
        SubAccount.profile_pic_url = imageurl

        val subaccount = firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(group_name)
        val account = firestore.collection("Account").document(currentUser()!!.email.toString())
        // Get a new write batch and commit all write operations
        firestore.runBatch { batch ->
            // Set the value of 'NYC'
            batch.set(subaccount, SubAccount)
            // Update the number of sub account
            batch.update(account, "sub_count", subaccount_num+1)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

    }



    fun observeUserData2(userId: String) {//예시
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


}