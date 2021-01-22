package com.example.makeyourcs.ui.user


import android.media.Image
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.ImageVo

class UserViewModel:ViewModel() {

    var imgList : MutableLiveData<ArrayList<ImageVo>> = MutableLiveData<ArrayList<ImageVo>>()
    val _imgList = ArrayList<ImageVo>()

    init{
        _imgList.add(ImageVo("1", R.drawable.ic_1,"60"))
        _imgList.add(ImageVo("2", R.drawable.ic_2,"61"))
        _imgList.add(ImageVo("3", R.drawable.ic_3,"62"))
        _imgList.add(ImageVo("4", R.drawable.ic_4,"63"))
        _imgList.add(ImageVo("5", R.drawable.ic_5,"64"))
        _imgList.add(ImageVo("6", R.drawable.ic_6,"65"))
        _imgList.add(ImageVo("7", R.drawable.ic_7,"66"))
        _imgList.add(ImageVo("8", R.drawable.ic_8,"67"))
        _imgList.add(ImageVo("9", R.drawable.ic_9,"68"))
        _imgList.add(ImageVo("10", R.drawable.ic_10,"69"))

        imgList.postValue(_imgList)
    }
}
