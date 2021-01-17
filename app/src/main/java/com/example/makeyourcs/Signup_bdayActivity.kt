package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.databinding.ActivitySignupBdayBinding
import com.example.makeyourcs.databinding.ActivitySignupEmailBinding
import com.example.makeyourcs.viewmodel.ViewModelCheckBday
import com.example.makeyourcs.viewmodel.ViewModelCheckDupEmail

class Signup_bdayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup_bday)

        val binding = DataBindingUtil.setContentView<ActivitySignupBdayBinding>(this, R.layout.activity_signup_bday)
        binding.vm = ViewModelCheckBday()
    }
}