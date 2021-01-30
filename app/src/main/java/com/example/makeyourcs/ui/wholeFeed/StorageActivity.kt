package com.example.makeyourcs.ui.wholeFeed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*


class StorageActivity : AppCompatActivity() {
//    private var binding: ActivityViewModelBinding? = null
//    private var viewModel: CounterViewModel? = null
    val GALLERY = 0 //GALLERY의 역할

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
//        binding.setActivity(this)
//        //ViewModel 가져오기
//        viewModel = ViewModelProviders.of(this).get(wholeFeedViewModel::class.java)
//        viewModel.counter.observe(this, object : Observer<?>() {
//            fun onChanged(imageUri: Uri) {
//                //UI 업데이트
//                binding.album_imageview.setImage("$imageUri ")
//            }
//        })
//
//        binding.countTextView.setText(viewModel.counter.toString() + " ")
        upload_photo.setOnClickListener{openAlbum()}
        //delete_photo.setOnClickListener{ deletePhoto() }
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
//            uploadPhoto(photoUri)
        }
    }
}

