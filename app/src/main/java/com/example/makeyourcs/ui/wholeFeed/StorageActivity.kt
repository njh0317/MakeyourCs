package com.example.makeyourcs.ui.wholeFeed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.makeyourcs.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.PlaceClass
import com.example.makeyourcs.data.PostClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detailed.view.*
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.tasks.await

class StorageActivity : AppCompatActivity() {
    val TAG = "FirebaseSource"
    val GALLERY = 0

    private val firebaseStorage: FirebaseStorage by lazy{
        FirebaseStorage.getInstance()
    }
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
        list_photo.setOnClickListener{ getMyPost() }
    }
    fun openAlbum(){  //저장된 사진을 공유...추후 사진 바로 찍어서 올리는 함수 추가 예정
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }

    //onCreate ->startActivityForResult -> (setResult) -> onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GALLERY){
            var photoUri = data?.data!! //photoUri 반환하여 뷰모델로 넘기기
            album_imageview.setImageURI(photoUri)

            //TODO:repository 에 접근해서 함수 호출로 수정
            var curPlace:PlaceClass = PlaceClass("공구", "대구북구경대", 85.0, 79.55)
            var curTag:PostClass.PictureClass.TagClass = PostClass.PictureClass.TagClass("조윤하", 5,5)
            setPost("dmlfid1348","dmlfid1348@naver.com","aaa",photoUri, curPlace, curTag)
        }
    }

    fun setPost(account:String, email:String, content:String, pAdd:Uri, place: PlaceClass, tag: PostClass.PictureClass.TagClass)
    {
        var posting = PostClass()
        var postPics = PostClass.PictureClass()

        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        var curId = "POST_" + timestamp
        posting.postId = curId
        posting.account = account
        posting.email = email
        posting.content = content
        posting.place_tag = place.place_name

        var order = 1
        val tagdb = firestore.collection("Post").document(posting.postId.toString()).collection("PictureClass").document(order.toString()).collection("TagClass").document(tag.tagged_id.toString())
        val placedb = firestore.collection("Place").document(place.place_name.toString())
        val picsdb = firestore.collection("Post").document(posting.postId.toString()).collection("PictureClass").document(order.toString())
        val finaldb = firestore.collection("Post").document(posting.postId.toString())

        var fileName = "IMAGE_" + timestamp + "_.png"
        var storageRef = firebaseStorage.reference.child("images/"+account+"/"+fileName)
        storageRef.putFile(pAdd!!).addOnSuccessListener {
            posting.imgUrl = pAdd.toString()
            postPics.order = 1
            postPics.picture_url = pAdd.toString()
            firestore.runBatch { batch ->
                batch.set(placedb, place)
                batch.set(tagdb, tag)
                batch.set(picsdb, postPics)
                batch.set(finaldb, posting)
            }.addOnSuccessListener {
                Log.d(TAG, "Transaction success!")
            }.addOnFailureListener { e ->
                Log.w(TAG, "Transaction failure.", e)
            }
        }
    }
    fun deletePhoto(email:String, postId: String){
        firebaseStorage.reference.child("images/"+email).child(postId).delete()
        //TODO firestore에서 삭제ㅎ
    }
    fun getMyPost()
    {
        val postlistLiveData = MutableLiveData<List<PostClass>>()
        try {//email = currentUser()!!.email.toString()
            firestore.collection("Post").whereEqualTo("email", "dmlfid1348@naver.com")
                .addSnapshotListener{ value, e ->
                    if(e!=null)
                    {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    value?.let{
                        val data = it?.toObjects(PostClass::class.java)
                        if (data != null) {
                            Log.w(TAG, "Listen Carefully.", e)
                            System.out.println(data)
                            postlistLiveData.postValue(data)
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting follower wait list", e)
        }

    }
}

