package com.example.makeyourcs.data.firebase

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.AccountPostClass
import com.example.makeyourcs.data.PostClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.accountlist_item.view.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class PostSource {
    val TAG = "FirebaseSource"
    val userDataLiveData = MutableLiveData<AccountClass>()
    val accountDataLiveData = MutableLiveData<List<AccountClass.SubClass>>()
    val followerWaitlistLiveData = MutableLiveData<List<AccountClass.Follower_wait_list>>()
    val postDataLiveData = MutableLiveData<PostClass>()

    val postlist = MutableLiveData<List<AccountPostClass.PostIdClass>>()
    val postlist2 : MutableList<AccountPostClass.PostIdClass> = arrayListOf()

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }
    private val firebaseStorage: FirebaseStorage by lazy{
        FirebaseStorage.getInstance()
    }
    fun currentUser() = firebaseAuth.currentUser

    fun observePostData() {
        System.out.println("observePostData: " + currentUser()!!.email)

        try {
            firestore.collection("Post").whereEqualTo("email", currentUser()!!.email.toString()).addSnapshotListener{ value, e ->
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


    fun setPost(account:String, email:String, content:String, place_tag:String, pAdd:Uri)
    {
        var posting = PostClass()
        var curId:String? = firebaseAuth?.uid
        posting.postId = curId
        posting.account = account
        posting.email = email
        posting.content = content
        posting.place_tag = place_tag

        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"//photoUri 받아서 뷰 모델에서 이름 설정
        //images를 폴더명으로 하고 있으나 업로드 유저 아이디를 폴더명으로 할 예정
        var storageRef = firebaseStorage.reference.child("images/"+account+"/"+fileName)
        storageRef.putFile(pAdd!!).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {uri ->
                if (curId != null) {
                    posting.imgUrl = uri.toString()
                    firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)
                }
            }
            Log.d(TAG, "Upload photo completed")
        }
    }

    suspend fun getPost(postId:Int):PostClass?
    {
        return try {
            val docRef = firestore.collection("Post").document(postId.toString())
            val doc = docRef.get().await()
            if (doc.exists()) {
                val post = doc.toObject(PostClass::class.java)
                Log.d(TAG, "post: " + post!!.postId)
                post
            } else {
                Log.w(TAG, "no data in Post")
                null
            }
        }
        catch(e:Throwable) {
            Log.w(TAG, "Error in Post", e)
            null
        }
    }

}