package com.example.makeyourcs.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.wholeFeed.StorageActivity
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.databinding.ActivityLoginBinding
import com.example.makeyourcs.utils.startMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.lang.Exception


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

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