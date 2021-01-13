package com.example.makeyourcs.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField

class ViewModelCheckBday {
    val bday = ObservableField<String>("")

    fun showText(view: View){
        Toast.makeText(view.context, "${bday.get()}", Toast.LENGTH_SHORT).show()

    }
}