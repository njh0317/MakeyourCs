package com.example.makeyourcs.ui.user.management

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.ui.MainActivity
import com.example.makeyourcs.ui.auth.AuthListener
import com.example.makeyourcs.ui.user.management.dialog.LimitedAccCntDialog
import com.example.makeyourcs.ui.user.management.follower.FollowerItem
import com.example.makeyourcs.ui.user.management.follower.SelectFollowerForNewAccActivity


class UserMgtViewModel (
    private val repository: AccountRepository
): ViewModel() {
    val TAG = "HOMEVIEWMODEL"
    private var _userData = MutableLiveData<AccountClass>()
    val userData: LiveData<AccountClass>
        get()= _userData
    private var _accountData = MutableLiveData<List<AccountClass.SubClass>>()
    val accountData: LiveData<List<AccountClass.SubClass>>
        get()= _accountData

    private var _followerData = MutableLiveData<List<String>>()
    val followerData: LiveData<List<String>>
        get() = _followerData

    var email: String? = null
    var id: String? = null
    var sub_count: Int? = null
    var following_num: Int? = null

    //sub account Info
    var subName = ObservableField<String>()
    var groupName = ObservableField<String>()
    var subIntroduce = ObservableField<String>()
    var subImg = ObservableField<String>()

    var authListener: AuthListener? = null

    val user by lazy {
        repository.currentUser()
    }

    fun getUserData() {
        System.out.println("getUserData")
        var data = repository.observeUserData()
        System.out.println("getUserData"+data.value)
        _userData = data
    }

    fun getAccountData() {
        System.out.println("getAccountData")
        var data = repository.observeAccountData()
        System.out.println("account Data: " + data.value)
        _accountData = data
    }

    fun getFollowerData(){
        System.out.println("getFollowerData")
        var data = repository.getAllfollower()
        System.out.println("follower Data: " + data.value)
        _followerData = data
    }

    fun getFollowerItemList(): ArrayList<FollowerItem>{ // 나를 팔로워하는 계정 리스트를 반환
        var itemlist = ArrayList<FollowerItem>()
        var data = followerData

        var follower = data.value?.iterator()
        if(follower != null){
            while(follower.hasNext()){
                var now = follower.next()
                itemlist.add(FollowerItem(R.drawable.ic_account, now.toString()))
            }
        }
        return itemlist
    }

    fun getItemList(): ArrayList<AccountMgtItem>{ // 부캐정보 값이 바뀌면 이 메소드 호출 -> recyclerItemList를 리턴
        var itemlist = ArrayList<AccountMgtItem>()
        var data = accountData
        //data.value?.sortedBy { it.sub_num }

        var account = data.value?.iterator()
        if(account != null){
            while(account.hasNext()){
//                Log.d("account","in Account")
                var now = account.next()
                itemlist.add(AccountMgtItem(R.drawable.ic_account, now.name.toString(), now.group_name.toString()))
            }
        }
        return itemlist
    }


    fun AddNewAccount(view: View){
//        System.out.println("new subAccount!!")
        var data = repository.observeUserData()

        if(subImg.get() != null){
            repository.setSubAccount(data.value?.sub_count!!, subName.get().toString(), groupName.get().toString(), subIntroduce.get().toString(), subImg.get().toString())
        }else{ // 부캐 프로필 사진 설정안했을 경우
            repository.setSubAccount(data.value?.sub_count!!, subName.get().toString(), groupName.get().toString(), subIntroduce.get().toString(), "default")
        }
//        view.context.startAccountMgtMainActivity()
        Intent(view.context, SelectFollowerForNewAccActivity::class.java).also {
            it.putExtra("groupname", groupName.get().toString())
            view.context.startActivity(it)
        }
    }

    fun DeleteAccount(delGroup: String){
        Log.d("UserMgtViewModel", "$delGroup - DeleteAccount")
        System.out.println("DeleteAccount in ViewModel")
        repository.delSubAccount(delGroup)
    }

    fun SetFollowertoSubaccount(context: Context, groupname: String, selected_follower: List<String>){
        System.out.println("in viewmodel: $groupname")
        Log.d("setfollower", "$selected_follower")

        repository.setSubaccountFollower(groupname, selected_follower)
        Log.d("setfollower", "success set follower")
        Intent(context, AccountMgtMainActivity::class.java).also{
            context.startActivity(it)
        }
    }

    fun goToAddNewAccount(view: View) {
        var data = repository.observeUserData()
        if(data.value?.sub_count == 3){
            val dialog =
                LimitedAccCntDialog(
                    view.context
                )
            dialog.WarningConfirm()
        }else{
            Intent(view.context, NewAccountMgtActivity::class.java).also {
                view.context.startActivity(it)
            }
        }
    }

    fun goToUserFeed(view: View){
        Intent(view.context, MainActivity::class.java).also{
            view.context.startActivity(it);
        }
//        var userFragment= UserFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.main_content,userFragment).commit()
    }

}