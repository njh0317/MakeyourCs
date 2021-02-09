package com.example.makeyourcs.ui.wholeFeed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.makeyourcs.R
import androidx.appcompat.app.AppCompatActivity
import com.example.makeyourcs.data.PostClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StorageActivity : AppCompatActivity() {
    val TAG = "FirebaseSource"
    val GALLERY = 0 //GALLERY의 역할
    val firebaseStorage = FirebaseStorage.getInstance();


    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }
    fun currentUser() = firebaseAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        upload_photo.setOnClickListener { openAlbum() }

//        delete_photo.setOnClickListener{ deletePhoto() }
//        list_photo.setOnClickListener {
//            var testList = ArrayList<String>()
//            GlobalScope.launch {
//                val testList = getPost(postId)
//            }
//            for (t: String in testList) {
//                System.out.println(t)
//            }
//        }
    }
    fun openAlbum(){  //저장된 사진을 공유...추후 사진 바로 찍어서 올리는 함수 추가 예정
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }

    /*fun uploadPhoto(photoUri: Uri) {

        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"//photoUri 받아서 뷰 모델에서 이름 설정
        //images를 폴더명으로 하고 있으나 업로드 유저 아이디를 폴더명으로 할 예정

        var storageRef = firebaseStorage.reference.child("images/"+fileName)
        var tmpid = 0;
        System.out.println("photoUri"+photoUri)

        val photo = hashMapOf(
            "photoUri" to photoUri.toString(),
            "order" to tmpid.toString()
        )

        //모델에서 다운로드
        storageRef.putFile(photoUri).addOnSuccessListener {
            Log.d("1", "Upload photo completed")
            firestore.collection("Post").document("1").collection("pictureClass").document("1")
                .set(photo)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
            .addOnFailureListener{e-> Log.w(TAG,"Retry to upload photo to storage", e) }

    }*/

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

    //onCreate ->startActivityForResult -> (setResult) -> onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GALLERY){
            var photoUri = data?.data!! //photoUri 반환하여 뷰모델로 넘기기
            album_imageview.setImageURI(photoUri)

            //TODO:repository 에 접근해서 함수 호출로 수정
            setPost("dmlfid1348","dmlfid1348@naver.com","aaa","aaa", photoUri)
        }
    }

    fun setPost(account:String, email:String, content:String, place_tag:String, pAdd:Uri)
    {
        var posting = PostClass()
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        var curId = "POST_" + timestamp
        posting.postId = curId
        posting.account = account
        posting.email = email
        posting.content = content
        posting.place_tag = place_tag

        var fileName = "IMAGE_" + timestamp + "_.png"
        var storageRef = firebaseStorage.reference.child("images/"+account+"/"+fileName)
        storageRef.putFile(pAdd!!).addOnSuccessListener {
            posting.imgUrl = pAdd.toString()
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)

            Log.d(TAG, "Upload photo completed")
        }
    }

    fun deletePhoto(email:String, postId: String){
        firebaseStorage.reference.child("images/"+email).child(postId).delete()
        //TODO firestore에서 삭제ㅎ
    }
}

