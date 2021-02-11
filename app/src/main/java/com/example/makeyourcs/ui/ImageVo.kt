package com.example.makeyourcs.ui

import android.view.View
import android.widget.Toast
import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_main.view.*

class ImageVo(val name: String, val photo: Int, val likes:String
              //, val postId: String,
//              val account:String,
//              val email:String,
//              val content:String,
//              val comment_cnt:Int ,
//              val imgUrl:String,
//              val is_stored:Boolean,
//              val place_tag:String
){
    fun onClickListener(view : View){
        Toast.makeText(view.context, "Clicked: $name", Toast.LENGTH_SHORT).show()
    }
}