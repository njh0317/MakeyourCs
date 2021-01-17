package com.example.makeyourcs

import android.app.AlertDialog
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField


class LoginViewModel {
    val et_username = ObservableField<String>("")
    val et_password = ObservableField<String>("")

    fun loginFail(view: View) {

        Log.i("input", "id: ${et_username.get()} , pw: ${et_password.get()}")

//        var builder = AlertDialog.Builder(view.context,R.style.MyAlertDialogStyle)
//        builder.setTitle("로그인할 수 없습니다.")
//        builder.setMessage("입력된 정보가 올바르지 않습니다.\n다시 입력하세요.")
//        builder.setPositiveButton("다시 시도", null)
//        builder.show()
    }

}