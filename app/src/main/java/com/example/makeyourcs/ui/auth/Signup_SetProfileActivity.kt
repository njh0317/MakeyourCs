package com.example.makeyourcs.ui.auth

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySignupSetProfileBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class Signup_SetProfileActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    lateinit var viewModel: AuthViewModel
    lateinit var binding: ActivitySignupSetProfileBinding

    val Gallery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_set_profile)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_set_profile)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        binding.changeimgbtn.setOnClickListener {
            loadImage()
        }
    }

    private fun loadImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Gallery){
            if(resultCode == RESULT_OK){
                var dataUri = data?.data
                try{
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                    binding.profileImg.setImageBitmap(bitmap)
                }catch (e: Exception){
                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                }
            }else{
                //something wrong
            }
        }
    }
}