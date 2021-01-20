package com.example.makeyourcs.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySignupBinding
import com.example.makeyourcs.ui.home.HomeActivity
import com.example.makeyourcs.utils.startHomeActivity
import com.example.makeyourcs.utils.startLoginActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
//        Intent(this, HomeActivity::class.java).also {
//            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(it)
//        }
    }

    override fun onSuccess() {
        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
    }
}