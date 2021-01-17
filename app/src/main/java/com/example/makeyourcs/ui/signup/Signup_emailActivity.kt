package com.example.makeyourcs.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySignupEmailBinding
import com.example.makeyourcs.databinding.ActivitySignupIdBinding
import com.example.makeyourcs.ui.auth.AuthViewModel
import com.example.makeyourcs.ui.auth.AuthViewModelFactory
import com.example.makeyourcs.viewmodel.ViewModelCheckDupEmail
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class Signup_emailActivity : AppCompatActivity() , KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_email)

        val binding: ActivitySignupEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_email)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

    }
}