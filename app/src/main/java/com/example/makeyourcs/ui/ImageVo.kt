package com.example.makeyourcs.ui

import android.view.View
import android.widget.Toast
import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_main.view.*

class ImageVo(val name: String, val photo:Drawable,val likes:String){
    fun onClickListener(view : View){
        Toast.makeText(view.context, "Clicked: $name", Toast.LENGTH_SHORT).show()
    }
}