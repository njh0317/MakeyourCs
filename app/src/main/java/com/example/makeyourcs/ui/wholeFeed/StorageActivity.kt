package com.example.makeyourcs.ui.wholeFeed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.postId
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*


class StorageActivity : AppCompatActivity() {
    private var binding: ActivityViewModelBinding? = null
    private var viewModel: CounterViewModel? = null
    val GALLERY = 0 //GALLERY의 역할

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
        binding.setActivity(this)
        //ViewModel 가져오기
        viewModel = ViewModelProviders.of(this).get(wholeFeedViewModel::class.java)
        viewModel.counter.observe(this, object : Observer<?>() {
            fun onChanged(imageUri: Uri) {
                //UI 업데이트
                binding.album_imageview.setImage("$imageUri ")
            }
        })

        binding.countTextView.setText(viewModel.counter.toString() + " ")
        upload_photo.setOnClickListener{openAlbum()}
        //delete_photo.setOnClickListener{ deletePhoto() }
    }


    //TODOs
    //1. 유저피드에 올라갈 사진의 주인 리스트 뽑기 *****
    //2. 리스트에 따라 이미지 불러와서 시간순으로 소팅
    fun setPhoto()
    {
        var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
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
    fun uploadPhoto(photoUri: Uri){//뷰 모델 + 모델(각 기능 확인하기)
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"//photoUri 받아서 뷰 모델에서 이름 설정
        //images를 폴더명으로 하고 있으나 업로드 유저 아이디를 폴더명으로 할 예정
        var storageRef = FirebaseStorage.getInstance().reference.child("images").child(fileName)
        var tmpid = 1;
        var firestore = FirebaseFirestore.getInstance().collection("Post")?.document(tmpid.toString())?.collection("pictureClass").document(tmpid.toString())?update(
            mapOf(
                "pictureUri" to photoUri.toString()
            )
        );

        //모델에서 다운로드
        storageRef.putFile(photoUri).addOnSuccessListener {
            Toast.makeText(this, "Upload photo completed", Toast.LENGTH_LONG).show()
        }

    }
    fun deletePhoto(){ //추후 delete하는 Activity에 추가
        FirebaseStorage.getInstance().reference.child("images").child(delete_filename_edittext.text.toString()).delete()
            .addOnSuccessListener{
                Toast.makeText(this, "Delete photo completed", Toast.LENGTH_LONG).show()
            }
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
            uploadPhoto(photoUri)
        }
    }
}

