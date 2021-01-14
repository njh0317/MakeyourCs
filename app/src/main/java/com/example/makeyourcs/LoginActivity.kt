package com.example.makeyourcs

import android.content.DialogInterface
import android.icu.text.LocaleDisplayNames
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.makeyourcs.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding=DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
        binding.vm=LoginViewModel()


    }
}