package com.example.makeyourcs.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySignupBdayBinding
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.ui.auth.AuthViewModel
import com.example.makeyourcs.ui.auth.AuthViewModelFactory
import com.example.makeyourcs.utils.startCelebrateActivity
import com.example.makeyourcs.utils.startHomeActivity
import com.example.makeyourcs.utils.startLoginActivity
import com.example.makeyourcs.viewmodel.ViewModelCheckBday
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class Signup_bdayActivity : AppCompatActivity() , KodeinAware, AuthListener {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_bday)

        val binding: ActivitySignupBdayBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_bday)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

    }

    override fun onStarted() {
    }

    override fun onSuccess() {
        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        startCelebrateActivity()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
        startLoginActivity()
    }
}