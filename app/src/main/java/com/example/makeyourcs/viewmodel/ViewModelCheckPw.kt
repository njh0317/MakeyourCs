package com.example.makeyourcs.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField

class ViewModelCheckPw {
    val pw = ObservableField<String>("")
    val checkpw = ObservableField<String>("")

    fun showText(view: View){
        Toast.makeText(view.context, "${pw.get()}, ${checkpw.get()}", Toast.LENGTH_SHORT).show()
    }

}