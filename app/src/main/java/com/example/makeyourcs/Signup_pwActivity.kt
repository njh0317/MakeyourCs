package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.databinding.ActivitySignupPwBinding
import com.example.makeyourcs.viewmodel.ViewModelCheckPw

class Signup_pwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup_pw)

        val binding = DataBindingUtil.setContentView<ActivitySignupPwBinding>(this, R.layout.activity_signup_pw)
        binding.vm = ViewModelCheckPw()
    }

}