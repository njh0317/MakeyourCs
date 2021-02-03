package com.example.makeyourcs.ui.auth

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityLoginBinding
import com.example.makeyourcs.utils.startMainActivity
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.InputStream


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware{
    override val kodein by kodein()
    private val TAG = "LOGINACTIVITY"
    private val factory: AuthViewModelFactory by instance()
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel
    private var mBtn : Button? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        mBtn = findViewById(R.id.photo_bt) as Button
        mBtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                System.out.println("button clicked !!")
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, 1)
            }
        })


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    var photoUri = data?.data!! //photoUri 반환하여 뷰모델로 넘기기
                    viewModel.setimage(photoUri)

                }
                catch(e:Exception)
                {
                    Log.w(TAG, "error in choose image ", e)

                }
            }
        }
    }


    override fun onStarted() {//대기상태, 로그인시 로딩 같은거 넣으면 됨..!!

    }

    override fun onStart(){ //처음에
        super.onStart()
        viewModel.user?.let {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            startMainActivity()
        }
    }

    override fun onSuccess() {//로그인 성공시
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        startMainActivity()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        System.out.println(message)
    }

}