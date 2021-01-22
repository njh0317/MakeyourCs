package com.example.makeyourcs.ui.home

import android.media.Image
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.ImageVo

class HomeViewModel:ViewModel() {


    private var imgList: MutableLiveData<List<ImageVo>>? = null

    internal fun getImgList(): MutableLiveData<List<ImageVo>> {
        if (imgList == null) {
            imgList = MutableLiveData()
            loadImg()
        }
        return imgList as MutableLiveData<List<ImageVo>>
    }

    private fun loadImg() {
        // do async operation to fetch users

    }

}
