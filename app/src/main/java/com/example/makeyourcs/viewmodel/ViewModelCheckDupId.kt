package com.example.makeyourcs.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField

class ViewModelCheckDupId {
    val id = ObservableField<String>("")

    fun showText(view: View){
        Toast.makeText(view.context, "${id.get()}", Toast.LENGTH_SHORT).show()
    }
}