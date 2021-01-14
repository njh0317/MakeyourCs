package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firestore = FirebaseFirestore.getInstance()
        val loginButton : Button = findViewById(R.id.login)
        loginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //함수를 적어 넣으세요.
                val id : EditText = findViewById(R.id.et_username)
                val pw : EditText = findViewById(R.id.et_password)
                makeAccount(id.text.toString(), pw.text.toString())

            }
        })

        val signupButton : Button = findViewById(R.id.signup)
        signupButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //함수를 적어 넣으세요.
                val id : EditText = findViewById(R.id.et_username)
                readAccount(id.text.toString())
            }
        })

    }
    fun readAccount(id:String)
    {

        try{
            firestore?.collection("Account")?.document(id)?.get()?.addOnCompleteListener{task->
                if(task.isSuccessful){
                    val account = AccountClass()
                    account.email = task.result!!["email"].toString()
                    account.pw = task.result!!["pw"].toString()
                    account.userId = task.result!!["userId"].toString()
                    account.following_num=task.result!!["following_num"].toString().toInt()
                    account.sub_count = task.result!!["sub_count"].toString().toInt()

                    System.out.println(account)
                }

            }
        }catch(e:Exception)
        {

        }

    }
    fun makeAccount(id:String, pw:String)
    {

        val account = AccountClass()
        account.email = id+"@gmail.com"
        account.pw = pw
        account.userId = id

        try {
            firestore?.collection("Account")?.document(account.userId!!)?.set(account)
        } catch(e:Exception)
        {
            Log.d("Errortag", e.toString())

        }

    }
    fun login(id:String, pw:String)
    {

    }
}