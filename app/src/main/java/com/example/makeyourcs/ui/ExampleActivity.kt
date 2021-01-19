package com.example.makeyourcs.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makeyourcs.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class ExampleActivity : AppCompatActivity() {
    var auth: FirebaseAuth?=null
    override fun onCreate(saveInstanceState: Bundle?){
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_example)
        auth = FirebaseAuth.getInstance()
        Log.d("TAG", "This is Example activity")
        val loginButton : Button = findViewById(R.id.login)
        loginButton.setOnClickListener{
            signin()
        }
        val registerButton : Button = findViewById(R.id.signup)
        registerButton.setOnClickListener{
            Signup()
        }

    }
    fun Signup(){
        Log.d("TAG", "signup is clicked")
        val id : EditText = findViewById(R.id.et_username)
        val pw : EditText = findViewById(R.id.et_password)
        auth?.createUserWithEmailAndPassword(id.text.toString()+"@com",pw.text.toString())
            ?.addOnCompleteListener{
            task->
                if(task.isSuccessful){
                    //회원가입성공
                    Log.d("TAG", "createUserWithEmail:success")
                }
                else {
                    //회원가입 실패
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
            ?.addOnFailureListener{
                Log.w("TAG", "ERROR:failure", it)
            }
    }

    fun signin(){
        Log.d("TAG", "signin is clicked")
        val id : EditText = findViewById(R.id.et_username)
        val pw : EditText = findViewById(R.id.et_password)
        auth?.signInWithEmailAndPassword(id.text.toString(), pw.text.toString())
            ?.addOnCompleteListener{
                task->
                if(task.isSuccessful){
                    //로그인 성공
                    Log.d("TAG", "signInWithEmail:success")
                }
                else{
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


}