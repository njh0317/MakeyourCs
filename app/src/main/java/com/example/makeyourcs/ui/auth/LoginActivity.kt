package com.example.makeyourcs.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.wholeFeed.StorageActivity
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.databinding.ActivityLoginBinding
import com.example.makeyourcs.utils.startHomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.lang.Exception


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel
    var firestore: FirebaseFirestore ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        firestore = FirebaseFirestore.getInstance()
        set_photo.setOnClickListener {
            val nextIntent = Intent(this, StorageActivity::class.java)
            startActivity(nextIntent)
        }

        set_posting.setOnClickListener{
            setPost()
        }

        get_posting.setOnClickListener{
            getPost(1)
        }

    }

    override fun onStarted() {//대기상태, 로그인시 로딩 같은거 넣으면 됨..!!

    }

    override fun onStart(){ //처음에
        super.onStart()
        viewModel.user?.let {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            startHomeActivity()
        }
    }

    override fun onSuccess() {//로그인 성공시
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        startHomeActivity()

    }

    override fun onFailure(message: String) {
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        System.out.println(message)
    }

    fun setPost()
    {

        var posting = PostClass()
        //postId++.also { posting.postId = it } //난수로 시스템에서 아이디생성
        posting.postId = 4;
        posting.post_account = "jihae9988"
        posting.content = "jihae가 99?"
        //posting.first_pic = "../images/test.jpg"
        posting.place_tag = "Fan in MacBook"
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
        }catch(e: Exception)
        {
            Log.d("cannot get", e.toString())
        }

    }
//    fun readAccount(id:String)
//    {
//
//        try{
//            firestore?.collection("Account")?.document(id)?.get()?.addOnCompleteListener{task->
//                if(task.isSuccessful){
//                    val account = AccountClass()
//                    account.email = task.result!!["email"].toString()
//                    account.pw = task.result!!["pw"].toString()
//                    account.userId = task.result!!["userId"].toString()
//                    account.following_num=task.result!!["following_num"].toString().toInt()
//                    account.sub_count = task.result!!["sub_count"].toString().toInt()
//
//                    System.out.println(account)
//                }
//
//            }
//        }catch(e:Exception)
//        {
//
//        }
//
//    }
//    fun makeAccount(id:String, pw:String)
//    {
//
//        val account = AccountClass()
//        account.email = id+"@gmail.com"
//        account.pw = pw
//        account.userId = id
//
//        try {
//            firestore?.collection("Account")?.document(account.userId!!)?.set(account)
//        } catch(e:Exception)
//        {
//            Log.d("Errortag", e.toString())
//
//        }
//
//    }
//    fun login(id:String, pw:String)
//    {
//
//    }
}