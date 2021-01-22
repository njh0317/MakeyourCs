package com.example.makeyourcs.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("imgRes")
fun imgLoad(view: ImageView, resId: Int){
    view.setImageResource(resId)
}