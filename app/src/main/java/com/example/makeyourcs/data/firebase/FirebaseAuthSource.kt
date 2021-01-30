package com.example.makeyourcs.data.firebase

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.postId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*

class FirebaseAuthSource {
    val TAG = "FirebaseSource"
    val userDataLiveData = MutableLiveData<AccountClass>()
    val postDataLiveData = MutableLiveData<PostClass>()
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }
    private val firebaseStorage: FirebaseStorage by lazy{
        FirebaseStorage.getInstance()
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
        firestore.collection("Account").document(account.userId.toString()).set(account)

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

    fun observeUserData() {
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






    //TODO:게시글 파트
    fun uploadPhoto(photoUri: Uri) {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"//photoUri 받아서 뷰 모델에서 이름 설정
        //images를 폴더명으로 하고 있으나 업로드 유저 아이디를 폴더명으로 할 예정
        var storageRef = firebaseStorage.reference.child("images/"+fileName)
//        var tmpid = 1;
//        var firestore = firestore.collection("Post")?.document(tmpid.toString())?.update(
//            mapOf(
//                "picture_url" to storageRef.toString()
//            )
//        );
        //모델에서 다운로드
        storageRef.putFile(photoUri).addOnSuccessListener {
            Log.d(TAG, "Upload photo completed")
        }
    }

    fun setPhoto() {
        var posting = PostClass()
        postId++.also { posting.postId = it } //난수로 시스템에서 아이디생성
        posting.post_account = "sobinsobin"
        posting.content = "life without fxxx coding^^"
        posting.first_pic = "../images/test.jpg"
        posting.place_tag = "homesweethome"
        try{
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)
        }
        catch (e: Exception){
            Log.d("cannot upload", e.toString())
        }

    }


    fun observePostData() {
        System.out.println("observePostData")
        System.out.println("observePostData2: " + currentUser()!!.email)

        try {
            firestore.collection("Post").whereEqualTo("post_account", currentUser()!!.email.toString()).addSnapshotListener{ value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    doc?.let {
                        val data = it?.toObject(PostClass::class.java)
                        System.out.println(data)
                        postDataLiveData.postValue(data)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }

    fun deletePhoto(){ //추후 delete하는 Activity에 추가
        FirebaseStorage.getInstance().reference.child("images").child(delete_filename_edittext.text.toString()).delete()
        Toast.makeText(this, "Delete photo completed", Toast.LENGTH_LONG).show()
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