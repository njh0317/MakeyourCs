package com.example.makeyourcs.utils

import android.content.Context
import android.content.Intent
import com.example.makeyourcs.ui.MainActivity
import com.example.makeyourcs.ui.auth.LoginActivity

fun Context.startHomeActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
