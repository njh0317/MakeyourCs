package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.PostClass
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


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
        posting.postId = 1 //난수로 시스템에서 아이디생성
        posting.post_account = "sobin"
        posting.content = "life without fxxx coding^^"
        posting.first_pic = "../images/test.jpg"
        posting.place_tag = "homesweethome"
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