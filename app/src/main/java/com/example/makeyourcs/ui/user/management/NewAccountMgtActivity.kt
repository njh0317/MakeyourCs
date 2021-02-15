package com.example.makeyourcs.ui.user.management

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityNewAccountMgtBinding
import com.squareup.okhttp.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NewAccountMgtActivity : AppCompatActivity() , KodeinAware {
    override val kodein by kodein()
    private val factory : UserMgtViewModelFactory by instance()
    lateinit var viewModel: UserMgtViewModel
    lateinit var binding: ActivityNewAccountMgtBinding

    var Gallery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account_mgt)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_account_mgt)
        viewModel = ViewModelProviders.of(this, factory).get(UserMgtViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.getUserData()
        viewModel.getAccountData()

        viewModel.userData.observe(this, Observer {
            binding.userName.text = "@" + it.userId
            Log.d("NEWACCOUNT", it.toString())
        })

        binding.cancelBtn.setOnClickListener{
            super.onBackPressed()
        }

        binding.changeSubimgbtn.setOnClickListener {
            loadImage()
        }
    }

    private fun loadImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Gallery){
            if(resultCode == RESULT_OK){
                var dataUri = data?.data
                binding.subimgFilepath.text = dataUri.toString()
                try{
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                    binding.subaccountImg.setImageBitmap(bitmap)
                }catch (e: Exception){
                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                }
            }else{
                //something wrong
            }
        }
    }

    public override fun onStart(){
        super.onStart()
    }
}