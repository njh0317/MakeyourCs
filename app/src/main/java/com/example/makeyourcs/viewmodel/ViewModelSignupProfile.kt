package com.example.makeyourcs.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField

class ViewModelSignupProfile {
    val name = ObservableField<String>("")
    val introduce = ObservableField<String>("")

    fun showText(view: View){
        Toast.makeText(view.context, "${name.get()}, ${introduce.get()}", Toast.LENGTH_LONG).show()
    }
}