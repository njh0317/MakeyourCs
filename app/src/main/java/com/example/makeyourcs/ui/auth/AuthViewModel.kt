package com.example.makeyourcs.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.signup.Signup_bdayActivity
import com.example.makeyourcs.ui.signup.Signup_emailActivity
import com.example.makeyourcs.ui.signup.Signup_idActivity
import com.example.makeyourcs.ui.signup.Signup_pwActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(
    private val repository: AccountRepository
): ViewModel() {
    //email and password for the input
    var email: String? = null
    var password: String? = null
    var checkpassword: String?=null
    var id: String? = null
    var year: Int? = null
    var month: Int? = null
    var day: Int? = null
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
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        //authentication started
        authListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = repository.login(email!!, password!!)
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

    //Doing same thing with signup
    fun signup() {
        System.out.println("Signup!")

        if (email.isNullOrEmpty() || password.isNullOrEmpty()|| id.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }
        authListener?.onStarted()
        val account = AccountClass()
        account.pw = password
        account.email = email
        account.userId = id
        account.year = year
        account.month = month
        account.day = day
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
        Intent(view.context, Signup_idActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    fun goToPw(view: View) {
        Intent(view.context, Signup_pwActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    fun goToEmail(view: View) {
        Intent(view.context, Signup_emailActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    fun goToBday(view: View) {
        Intent(view.context, Signup_bdayActivity::class.java).also {
            view.context.startActivity(it)
        }
    }



    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}