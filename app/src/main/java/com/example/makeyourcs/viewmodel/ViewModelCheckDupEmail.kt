package com.example.makeyourcs.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField

class ViewModelCheckDupEmail {
    val email = ObservableField<String>("")

    fun showText(view: View){
        Toast.makeText(view.context, "${email.get()}", Toast.LENGTH_SHORT).show()
    }
}