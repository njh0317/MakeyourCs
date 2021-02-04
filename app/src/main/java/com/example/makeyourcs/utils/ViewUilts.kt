package com.example.makeyourcs.utils

import android.content.Context
import android.content.Intent
import com.example.makeyourcs.ui.MainActivity
import com.example.makeyourcs.ui.auth.LoginActivity
import com.example.makeyourcs.ui.auth.Signup_SetProfileActivity
import com.example.makeyourcs.ui.user.management.AccountMgtMainActivity

fun Context.startMainActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startAccountMgtMainActivity() =
    Intent(this, AccountMgtMainActivity::class.java).also{
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startSetOriginProfile() =
    Intent(this, Signup_SetProfileActivity::class.java).also{
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }