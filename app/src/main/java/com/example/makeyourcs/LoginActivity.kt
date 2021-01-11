package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.makeyourcs.data.AccountClass


class LoginActivity : AppCompatActivity() {
    var firestore : FirebaseFireStore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton : Button = findViewById(R.id.login)
        loginButton.setOnClickListener{
            makeAccount()

        }

    }
    fun makeAccount()
    {
        var account = AccountClass()
        account.email = "njnjh0317@gmail.com"
        account.pw = "1234"
        account.userId = "njh0317"


    }
}