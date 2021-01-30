package com.example.makeyourcs.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide

@BindingAdapter("imgRes")
fun imgLoad(view: ImageView, resId: Int){
//    Glide.with(view.context)
//        .load(resId)
//        .dontTransform()
//        .into(view)

    view.setImageResource(resId)

}