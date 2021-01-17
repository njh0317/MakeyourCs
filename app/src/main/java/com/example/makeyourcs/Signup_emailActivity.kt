package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.databinding.ActivitySignupEmailBinding
import com.example.makeyourcs.viewmodel.ViewModelCheckDupEmail

class Signup_emailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup_email)

        val binding = DataBindingUtil.setContentView<ActivitySignupEmailBinding>(this, R.layout.activity_signup_email)
        binding.vm = ViewModelCheckDupEmail()

    }
}