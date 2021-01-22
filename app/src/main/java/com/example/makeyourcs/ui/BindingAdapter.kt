package com.example.makeyourcs.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageDrawble")
fun bindImageFromRes(view: ImageView, drawble : Drawable?){
    view.setImageDrawable(drawble)
}
