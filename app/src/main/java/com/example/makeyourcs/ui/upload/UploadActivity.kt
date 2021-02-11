package com.example.makeyourcs.ui.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.makeyourcs.R
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM=0
    var photoUri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        //open the album
        var intent = Intent(Intent.ACTION_PICK)
        intent.type= MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent,PICK_IMAGE_FROM_ALBUM)

        //add image upload event
        share_btn.setOnClickListener{
            Log.d("upload","upload click listener")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_FROM_ALBUM){
            if(resultCode== Activity.RESULT_OK){
                //This it path to the selected image
                photoUri=data?.data
                upload_image.setImageURI(photoUri)
            }
            else{
                //exit the addPhotoActivity if you leave the album without selecting it
                finish()
            }
        }
    }
}