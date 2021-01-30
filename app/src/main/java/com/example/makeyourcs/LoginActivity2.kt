package com.example.makeyourcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.ui.wholeFeed.StorageActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

var postId : Int = 1;
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
        set_photo.setOnClickListener {
            val nextIntent = Intent(this, StorageActivity::class.java)
            startActivity(nextIntent)
        }
        val setButton : Button = findViewById(R.id.set_posting)
        setButton.setOnClickListener{
            setPost()
        }
        val getButton : Button = findViewById(R.id.get_posting)
        getButton.setOnClickListener{
            getPost(1)
        }
    }
    fun makeAccount()
    {
        var account = AccountClass()
        account.email = "test@gmail.com"
        account.pw = "112345"
        account.userId = "forTest"
        firestore?.collection("Account")?.document(account.userId.toString())?.set(account)


    }

}