package com.example.makeyourcs.ui.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityUploadBinding
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.user.management.UserMgtViewModel
import com.example.makeyourcs.ui.user.management.UserMgtViewModelFactory
import kotlinx.android.synthetic.main.activity_upload.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class UploadActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory : UploadViewModelFactory by instance()

    var PICK_IMAGE_FROM_ALBUM=0
    var photoUri: Uri?=null
    lateinit var binding: ActivityUploadBinding
    private lateinit var viewModel: UploadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload)
        viewModel = ViewModelProviders.of(this, factory).get(UploadViewModel::class.java)
        //binding.viewmodel=viewModel

        //open the album
        var intent = Intent(Intent.ACTION_PICK)
        intent.type= MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent,PICK_IMAGE_FROM_ALBUM)

        //back button
        binding.backBtn.setOnClickListener{
            super.onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_FROM_ALBUM){
            if(resultCode== Activity.RESULT_OK){
                //This it path to the selected image
                photoUri=data?.data
                binding.uploadImage.setImageURI(photoUri)
            }
            else{
                //exit the addPhotoActivity if you leave the album without selecting it
                finish()
            }
        }
    }
}