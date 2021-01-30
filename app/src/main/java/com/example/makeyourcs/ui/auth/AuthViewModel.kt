package com.example.makeyourcs.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import java.util.*

class AuthViewModel(
    private val repository: AccountRepository
): ViewModel() {
    //email and password for the input
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var checkpassword = ObservableField<String>()
    var id = ObservableField<String>()
    var year: Int? = null
    var month: Int? = null
    var day: Int? = null
    var birthday = ObservableField<String>()

    //auth listener
    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    //function to perform login
    fun login() {

        //validating email and password
        if (email.get().isNullOrEmpty() || password.get().isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        //authentication started
        authListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = repository.login(email.get()!!, password.get()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess()
            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun removeid(){
        id.set("")

    }
    fun removepw(){
        password.set("")

    }
    fun removecpw(){
        checkpassword.set("")
    }
    fun removeemail(){
        email.set("")
    }
    fun removebday(){
        birthday.set("")
    }

    //Doing same thing with signup
    fun signup() {
        if (email.get().isNullOrEmpty() || password.get().isNullOrEmpty()|| id.get().isNullOrEmpty()|| checkpassword.get().isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }

        if(password.get() != checkpassword.get()) //패스워드 확인
        {
            authListener?.onFailure("Password not matched")
            return
        }

        if (!birthday.get().isNullOrEmpty()){
            val arr = birthday?.get().toString().split(".")
            if (arr != null) {
                year = Integer.parseInt(arr.get(0))
                month = Integer.parseInt(arr.get(1))
                day = Integer.parseInt(arr.get(2))
                val b_cal = Calendar.getInstance()//birthday
                val n_cal = Calendar.getInstance()//today
                b_cal.set(Calendar.YEAR, year!!)
                b_cal.set(Calendar.MONTH, month!!)
                b_cal.set(Calendar.DATE, day!!)

                if(b_cal.after(n_cal))
                {
                    authListener?.onFailure("Enter correct Birthday")
                    return
                }
            }
        }
        authListener?.onStarted()
        val account = AccountClass()

        account.pw = password.get().toString()
        account.email = email.get().toString()
        account.userId = id.get().toString()
//        account.year = year
//        account.month = month
//        account.day = day

        System.out.println(account)
        val disposable = repository.register(account)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}