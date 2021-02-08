package com.example.makeyourcs.ui.wholeFeed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.makeyourcs.R
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        upload_photo.setOnClickListener{openAlbum()}
        list_photo.setOnClickListener{
            var testList = ArrayList<String>()
            testList = photoList()
            for(t:String in testList){
                System.out.println(t)
            }
        }
    }


    fun uploadPhoto(photoUri: Uri) {

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

    }
    fun photoList(): ArrayList<String>{
        var posting = ArrayList<String>()
        try{
            for(i in 0..1) {
                System.out.println(i.toString())
                firestore?.collection("Post")?.document("1")?.collection("pictureClass").document(i.toString()).get()?.addOnCompleteListener{task->
                    if(task.isSuccessful){
                        var src = task.result!!["photoUri"].toString()
                        posting.add(src)
//                        System.out.println(src)
                    }
                }
            }
            System.out.println("This is ArrayList")
            System.out.println(posting)
        }catch(e: java.lang.Exception)
        {
            Log.d("cannot get", e.toString())
        }
        return posting
    }
    fun openAlbum(){  //저장된 사진을 공유...추후 사진 바로 찍어서 올리는 함수 추가 예정
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)

        //그냥 startActivityForResult(intent)해도 되지만, 액티비티 구별을 위하여 requestCode 쓰려면 startActivityForResult
    }
    //onCreate ->startActivityForResult -> (setResult) -> onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
    //requestCode는 다른 액티비티로 부터 값을 주고받을 때 뿐만 아니라,
    // 응답 활동을 바로 결과 전송이 가능하다는 장점이 있다.
    // 예를 들어 메인액티비티에서 여러 다른 액티비티들을 실행할 수 있고,
    // 각 액티비티에서 결과를 받아야하는 경우에 사용하면 구분하기 쉽다.
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
            posting.imgUrl = pAdd.toString()
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)

            Log.d(TAG, "Upload photo completed")
        }
    }
}

