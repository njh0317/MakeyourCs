package com.example.makeyourcs.data.firebase

import android.accounts.Account
import android.content.ContentValues
import android.util.Log
import com.example.makeyourcs.data.AccountClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class FirebaseAuthSource {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }


    fun login(userID: String, password: String) = Completable.create { emitter ->
        var email = findEmailbyUserID(userID)
        Log.d("TAG", email)
        firebaseAuth.signInWithEmailAndPassword(email!!, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun findEmailbyUserID(userID: String):String?{
        System.out.println("findEmailbyUserID")
        var email : String?=null
        firestore.collection("Account").whereEqualTo("userId",userID).get().addOnCompleteListener{task->
            if(task.isSuccessful){
                for(dc in task.result!!.documents){
                    var account =dc.toObject(AccountClass::class.java)
                    System.out.println(account)
                    email = account?.email.toString()
                }
            }

        }.addOnFailureListener { exception ->
            Log.e("HHTT", "Error: " + exception.toString())
        }

        System.out.println("findEmailbyUserID")
        return email
    }

    fun register(account: AccountClass) = Completable.create { emitter ->
        System.out.println(account)
        firestore.collection("Account").document(account.userId.toString()).set(account)
        firebaseAuth.createUserWithEmailAndPassword(account.email.toString(), account.pw.toString()).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                    System.out.println("insert success")
                }
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

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

}