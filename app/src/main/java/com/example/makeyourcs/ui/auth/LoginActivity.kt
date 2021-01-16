package com.example.makeyourcs.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityLoginBinding
import com.example.makeyourcs.utils.startHomeActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this


    }

    override fun onStarted() {
        super.onStart()
        viewModel.user?.let {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            startHomeActivity()
        }
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
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