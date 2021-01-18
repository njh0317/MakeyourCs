package com.example.makeyourcs

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_storage.*
import android.content.Intent as Intent

class StorageActivity : AppCompatActivity() {
    val GALLERY = 0
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        System.out.println("come in")
        upload_photo.setOnClickListener{openAlbum()}
    }
    fun openAlbum(){
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }
}