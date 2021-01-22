package com.example.makeyourcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.PostClass
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
    fun setPost()
    {
        var posting = PostClass()
        postId++.also { posting.postId = it } //난수로 시스템에서 아이디생성
        posting.post_account = "dmlfid1348"
        posting.content = "희루가기시러"
        //posting.first_pic = "../images/test.jpg"
        posting.place_tag = "huiru"
        try{
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)
        }
        catch(e: Exception){
            Log.d("cannot upload", e.toString())
        }

    }
    fun getPost(postId:Int)
    {
        try{
            firestore?.collection("Post")?.document(postId.toString())?.get()?.addOnCompleteListener{task->
                if(task.isSuccessful){
                    val posting = PostClass()
                    posting.postId = task.result!!["postId"].toString().toInt()
                    posting.post_account = task.result!!["post_account"].toString()
                    posting.content = task.result!!["content"].toString()
                    posting.first_pic = task.result!!["first_pic"].toString()

                    System.out.println(posting)
                }

            }
        }catch(e:Exception)
        {
            Log.d("cannot get", e.toString())
        }

    }
}