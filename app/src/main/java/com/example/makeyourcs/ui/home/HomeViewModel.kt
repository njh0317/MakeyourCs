package com.example.makeyourcs.ui.home

import android.util.Log
import android.view.View
import android.widget.Toast
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.ui.auth.SignupActivity
import com.example.makeyourcs.ui.user.management.AccountMgtMainActivity
import com.example.makeyourcs.utils.startAccountMgtMainActivity
import com.example.makeyourcs.utils.startLoginActivity

class HomeViewModel(
    private val repository: AccountRepository
):ViewModel() {
    val TAG = "HOMEVIEWMODEL"

    private var _userData = MutableLiveData<AccountClass>()
    val userData: LiveData<AccountClass>
        get() = _userData

    private var _accountData = MutableLiveData<List<AccountClass.SubClass>>()
    val accountData: LiveData<List<AccountClass.SubClass>>
        get() = _accountData

    var email: String? = null
    var id: String? = null
    var sub_count: Int? = null
    var following_num: Int? = null

    //sub account Info
    var subName = ObservableField<String>()
    var groupName = ObservableField<String>()
    var subIntroduce = ObservableField<String>()

    //auth listener
    var authListener: AuthListener? = null


    var imgList : MutableLiveData<ArrayList<ImageVo>> = MutableLiveData<ArrayList<ImageVo>>()
    val _imgList = ArrayList<ImageVo>()

    init{
        _imgList.add(ImageVo("1", R.drawable.ic_1,60.toString()))
        _imgList.add(ImageVo("2", R.drawable.ic_2,61.toString()))
        _imgList.add(ImageVo("3", R.drawable.ic_3,62.toString()))
        _imgList.add(ImageVo("4", R.drawable.ic_4,63.toString()))
        _imgList.add(ImageVo("5", R.drawable.ic_5,64.toString()))
        _imgList.add(ImageVo("6", R.drawable.ic_6,65.toString()))
        _imgList.add(ImageVo("7", R.drawable.ic_7,66.toString()))
        _imgList.add(ImageVo("8", R.drawable.ic_8,67.toString()))
        _imgList.add(ImageVo("9", R.drawable.ic_9,68.toString()))
        _imgList.add(ImageVo("10", R.drawable.ic_10,69.toString()))

        imgList.postValue(_imgList)
    }

    val user by lazy {
        repository.currentUser()
    }

    fun newAccount(view: View) {
        System.out.println("new subAccount!!")
        var data = repository.observeUserData()

        repository.setSubAccount(
            data.value?.sub_count!!,
            subName.get().toString(),
            groupName.get().toString(),
            subIntroduce.get().toString(),
            "default"
        )
        view.context.startAccountMgtMainActivity()
    }

    fun goToUserFeedAccountMgt(view: View) {
        view.context.startAccountMgtMainActivity()
//        Intent(view.context, AccountMgtMainActivity::class.java).also {
//            view.context.startActivity(it)
//        }
    }

}
