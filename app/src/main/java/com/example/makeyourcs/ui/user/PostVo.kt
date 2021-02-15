package com.example.makeyourcs.ui.user

import android.view.View
import android.widget.Toast
import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_main.view.*

class PostVo(var postId: String,
             var account:String,
             var email:String,
             var content:String,
             var like:Int,
             var comment_cnt:Int,
             var imgUrl:String,
             var is_stored:Boolean,
             var place_tag:String
){
}