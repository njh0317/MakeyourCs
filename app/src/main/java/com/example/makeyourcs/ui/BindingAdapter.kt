package com.example.makeyourcs.ui

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//import com.bumptech.glide.Glide

@BindingAdapter("imgRes")
fun imgLoad(view: ImageView, resId: Int){
//    Glide.with(view.context)
//        .load(resId)
//        .dontTransform()
//        .into(view)

    view.setImageResource(resId)

}
@BindingAdapter("imgResString")
fun imgLoadString(view: ImageView, resId: Uri){
    Log.d("resId","resId : "+ resId)
//    Glide.with(view.context)
//        .load(resId.toUri())
//        .dontTransform()
//        .into(view)

//    view.setImageResource(resId)
    view.setImageURI(resId)

}