package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.databinding.ActivitySignupIdBinding
import com.example.makeyourcs.viewmodel.ViewModelCheckDupId

class Signup_idActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup_id)

        val signupidbinding = DataBindingUtil.setContentView<ActivitySignupIdBinding>(this, R.layout.activity_signup_id)
        signupidbinding.vm = ViewModelCheckDupId()

    }
}