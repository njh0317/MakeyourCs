package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.makeyourcs.data.AccountClass
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    var firestore : FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firestore = FirebaseFirestore.getInstance()
        val loginButton : Button = findViewById(R.id.login)
        loginButton.setOnClickListener{
            makeAccount()

        }

    }
    fun makeAccount()
    {
        var account = AccountClass()
        account.email = "sobin@gmail.com"
        account.pw = "2345"
        account.userId = "sobin1234"
        firestore?.collection("Account")?.document(account.userId.toString())?.set(account)


    }
}