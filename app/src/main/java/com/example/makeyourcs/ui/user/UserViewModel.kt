package com.example.makeyourcs.ui.user

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.ImageVo
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.utils.startLoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class UserViewModel(
    private val repository: AccountRepository
) :ViewModel() {
    val TAG = "USERVIEWMODEL"
    private var _userData = MutableLiveData<AccountClass>()
    val userData: LiveData<AccountClass>
        get()= _userData

    private var _accountData = MutableLiveData<AccountClass.SubClass>()
    val accountData: LiveData<AccountClass.SubClass>
        get() = _accountData


    var email: String? = null
    var id: String? = null
    var sub_count: Int? = null
    var following_num: Int? = null
    //auth listener
    var authListener: AuthListener? = null

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
    val user by lazy {
        repository.currentUser()
    }

    fun getUserData() {
        System.out.println("getUserData")
        var data = repository.observeUserData()
        System.out.println("getUserData "+data.value)
        _userData = data
    }

    fun getAccountData(gname : String) {
        System.out.println("getAccountData")
        var data = repository.observeoneAccountData(gname)
        System.out.println("getAccountData " + data.value)
        _accountData = data
    }

    fun logout(view: View) {
        repository.logout()
        view.context.startLoginActivity()
    }

    suspend fun getImageurl(imagename:String):Uri? {

//        GlobalScope.launch{
//            uri = repository.imageurl(imagename)
//        }
        return repository.imageurl(imagename)
    }

}
