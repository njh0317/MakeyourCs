package com.example.makeyourcs.ui.home

import android.media.Image
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        var userList = arrayListOf<ImageVo>(
            ImageVo("1",  "ic_1"),
            ImageVo("2",  "ic_2"),
            ImageVo("3", "ic_3"),
            ImageVo("4",  "ic_4"),
            ImageVo("5",  "ic_5"),
            ImageVo("6",  "ic_6"),
            ImageVo("7",  "ic_7"),
            ImageVo("8",  "ic_8"),
            ImageVo("9",  "ic_9"),
            ImageVo("10",  "ic_10"),
            ImageVo("11",  "ic_11"),
            ImageVo("12",  "ic_1"),
            ImageVo("13",  "ic_2"),
            ImageVo("14", "ic_3"),
            ImageVo("15", "ic_4")
        )

    }

}
