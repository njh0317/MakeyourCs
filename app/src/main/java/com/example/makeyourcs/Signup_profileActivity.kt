package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.databinding.ActivitySignupProfileBinding
import com.example.makeyourcs.viewmodel.ViewModelSignupProfile

class Signup_profileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup_profile)

        val binding = DataBindingUtil.setContentView<ActivitySignupProfileBinding>(this, R.layout.activity_signup_profile)
        binding.vm = ViewModelSignupProfile()
    }
}