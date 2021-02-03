package com.example.makeyourcs.data.firebase

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class   FirebaseAuthSource {
    val TAG = "FirebaseSource"
    val userDataLiveData = MutableLiveData<AccountClass>()
    val accountsDataLiveData = MutableLiveData<List<AccountClass.SubClass>>()
    val accountDataLiveData = MutableLiveData<AccountClass.SubClass>()
    val followerWaitlistLiveData = MutableLiveData<List<AccountClass.Follower_wait_list>>()
    val followlistLiveData = MutableLiveData<List<AccountClass.FollowClass>>()

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }
    private val firestorage: FirebaseStorage by lazy{
        FirebaseStorage.getInstance()
    }


    fun login(email: String, password: String) = Completable.create { emitter ->
        Log.d("TAG", email)
        firebaseAuth.signInWithEmailAndPassword(email!!, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
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
        //TODO: 사용자 부캐안에 게시글 삭제
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

    fun setOriginAccount(name:String, introduction:String, imageurl:Uri) { //TODO: 사진 url :default
        val OriginAccount = AccountClass.SubClass()
        OriginAccount.name = name
        OriginAccount.introduction = introduction
        OriginAccount.sub_num = 0
        OriginAccount.group_name = "본 계정"
        OriginAccount.profile_pic_url = "default"

        if(imageurl.toString() != "default"){
            OriginAccount.profile_pic_url = uploadprofile(imageurl).toString()
        }


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
    fun delSubAccount(group_name:String) {
        val subaccount = firestore
            .collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(group_name)
        val account = firestore.collection("Account")
            .document(currentUser()!!.email.toString())

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(account)
            val newSubcount = Integer.parseInt(snapshot.get("sub_count")!!.toString()) - 1
            // Update the number of sub account
            transaction.delete(subaccount)
            transaction.update(account, "sub_account", newSubcount)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }
    fun observeoneAccountData(group_name: String)
    {
        try{
            firestore.collection("Account")
                .document(currentUser()!!.email.toString())
                .collection("SubAccount")
                .document(group_name)
                .addSnapshotListener{value, e ->
                    if(e!=null)
                    {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    value?.let{
                        val data = it?.toObject(AccountClass.SubClass::class.java)
                        accountDataLiveData.postValue(data)
                    }

                }
        }catch(e: Exception)
        {
            Log.e(TAG, "Error getting one account data", e)
        }

    }
    fun observeAccountsData() {
        var subaccountlist : ArrayList<AccountClass.SubClass> = arrayListOf()
        try {
            firestore.collection("Account")
                .document(currentUser()!!.email.toString())
                .collection("SubAccount")
                .orderBy("sub_num")
                .addSnapshotListener{ value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                    subaccountlist.clear()
                for (doc in value!!) {
                    doc?.let {
                        val data = it?.toObject(AccountClass.SubClass::class.java)
                        subaccountlist.add(data)
                    }
                }
                    accountsDataLiveData.postValue(subaccountlist)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun follow(toEmail:String)
    {
        val from = AccountClass.FollowClass()
        from.to_account = toEmail
        from.to_account_sub = hashMapOf("default" to FALSE)

        val to = AccountClass.Follower_wait_list()
        to.follow_date = LocalDateTime.now()
        to.from_account = currentUser()!!.email.toString()

        val fromAccount = firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("Follow")
            .document(toEmail)
        val toAccount = firestore.collection("Account")
            .document(toEmail)
            .collection("Follower_wait_list")
            .document(currentUser()!!.email.toString())

        firestore.runBatch { batch ->
            batch.set(toAccount, to)
            batch.set(fromAccount, from)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

    }

    fun observefollowerWaitList()
    {
        var followerwaitlist : ArrayList<AccountClass.Follower_wait_list> = arrayListOf()
        try {
            firestore.collection("Account")
                .document(currentUser()!!.email.toString())
                .collection("Follower_wait_list")
                .addSnapshotListener{ value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    for (doc in value!!) {
                        doc?.let {
                            val data = it?.toObject(AccountClass.Follower_wait_list::class.java)
                            followerwaitlist.add(data)

                        }
                    }
                    followerWaitlistLiveData.postValue(followerwaitlist)
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting follower wait list", e)
        }

    }
    fun observefollowList()
    {
        var followlist : ArrayList<AccountClass.FollowClass> = arrayListOf()
        try {
            firestore.collection("Account")
                .document(currentUser()!!.email.toString())
                .collection("Follow")
                .addSnapshotListener{ value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    for (doc in value!!) {
                        doc?.let {
                            val data = it?.toObject(AccountClass.FollowClass::class.java)
                            followlist.add(data)

                        }
                    }
                    followlistLiveData.postValue(followlist)
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting follower wait list", e)
        }

    }
    fun make_map(group_name_list:List<String>): HashMap<String, Boolean> {
        val hashMap:HashMap<String,Boolean> = HashMap<String, Boolean>() //define empty hashmap

        for(group in group_name_list)
        {
            hashMap.put(group, TRUE)
        }
        return hashMap
    }
    fun notacceptfollow(fromEmail:String)
    {
        val toAccount_waitlist = firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("Follower_wait_list")
            .document(fromEmail)

        val fromAccount = firestore.collection("Account")
            .document(fromEmail)
            .collection("Follow")
            .document(currentUser()!!.email.toString())

        firestore.runTransaction { transaction ->
            transaction.delete(toAccount_waitlist) // waitlist 에서 삭제
            transaction.delete(fromAccount)
        }
            .addOnSuccessListener { Log.d(TAG, "accept follow Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }


    }
    fun acceptfollow(fromEmail:String, group_name_list:List<String>)
    {
        val hashmap = make_map(group_name_list)
        val toAccount_waitlist = firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("Follower_wait_list")
            .document(fromEmail)

        val fromAccount = firestore.collection("Account")
            .document(fromEmail)
            .collection("Follow")
            .document(currentUser()!!.email.toString())

        val follower_list : ArrayList<AccountClass.SubClass> = arrayListOf()
        firestore.runTransaction { transaction ->
            for (group_name in group_name_list){
                var toAccount = firestore.collection("Account")
                    .document(currentUser()!!.email.toString())
                    .collection("SubAccount")
                    .document(group_name)
                val snapshot = transaction.get(toAccount)
                val data = snapshot?.toObject(AccountClass.SubClass::class.java)
                if (data != null) {
                    follower_list.add(data)
                }
            }
            transaction.delete(toAccount_waitlist) // waitlist 에서 삭제
            transaction.update(fromAccount, "to_account_sub", hashmap)

            var check_num = 0
            for (group_name in group_name_list){
                val toAccount = firestore.collection("Account")
                    .document(currentUser()!!.email.toString())
                    .collection("SubAccount")
                    .document(group_name)
                follower_list[check_num].follower.put(fromEmail, true)
                follower_list[check_num].follower_num = follower_list[check_num].follower_num?.plus(
                    1
                )
                transaction.set(toAccount, follower_list[check_num])
                check_num += 1
            }
        }
            .addOnSuccessListener { Log.d(TAG, "accept follow Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }


    }


    fun modifiedprofile(group_name:String, name:String, introduction:String, imageurl:String)
    {
        val DocRef = firestore
            .collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(group_name)

        firestore.runTransaction { transaction ->
            transaction.update(DocRef, "introduction", introduction)
            transaction.update(DocRef, "name", name)
            transaction.update(DocRef, "profile_pic_url", imageurl)
            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

    }

    fun uploadprofile(filepath:Uri):Uri? {
        var downloadUri: Uri? = null
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"//photoUri 받아서 뷰 모델에서 이름 설정
        var storageRef = firestorage.reference.child("profile/" + fileName)
        var uploadTask = storageRef.putFile(filepath)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result
            } else {
                Log.w(TAG, "uploadprofile failure")
            }
        }
        return downloadUri
    }


}