package com.example.makeyourcs.data.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.example.makeyourcs.data.AccountClass
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSource {
    private val firestore = FirebaseFirestore.getInstance()

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

    fun confirmID(userId:String, userPw:String) : Boolean
    {
        val docRef = firestore.collection("Account").document(userId)
        var flag:Boolean = false
        docRef.get()
            .addOnSuccessListener { document ->
                if(document!=null)
                {
                    System.out.println(document)


                }else{
                    flag = document!=null

                }
            }
            .addOnFailureListener{ exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return flag

    }




}