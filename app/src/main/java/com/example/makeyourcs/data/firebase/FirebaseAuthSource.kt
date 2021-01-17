package com.example.makeyourcs.data.firebase

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


    fun login(email: String, password: String) = Completable.create { emitter ->
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

    fun register(account: AccountClass, subaccount:AccountClass.SubClass) = Completable.create { emitter ->
        System.out.println(account)
        System.out.println(subaccount)
        firestore.collection("Account").document(account.userId.toString()).set(account)
        firestore.collection("Account")
            .document(account.userId.toString())
            .collection("SubAccount")
            .document(account.sub_count.toString()).set(subaccount)
        firebaseAuth.createUserWithEmailAndPassword(account.email.toString(), account.pw.toString()).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {

                    System.out.println("insert success")
//                    firestore.collection("Account").document(account.userId.toString()).set(account)
                    firestore.collection("Account")
                        .document(account.userId.toString())
                        .collection("SubAccount")
                        .document(account.sub_count.toString()).set(subaccount)

                    emitter.onComplete()
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