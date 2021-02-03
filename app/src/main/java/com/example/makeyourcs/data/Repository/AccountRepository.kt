package com.example.makeyourcs.data.Repository

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.firebase.FirebaseAuthSource

class AccountRepository(
    private val firestore: FirebaseAuthSource
) {
    fun login(email: String, password: String) = firestore.login(email, password)

    fun register(account: AccountClass) = firestore.register(account)

    fun currentUser() = firestore.currentUser()

    fun logout() = firestore.logout()

    fun observeUserData(): MutableLiveData<AccountClass> // 계정 정보 가져오기
    {
        firestore.observeUserData()
        var data = firestore.userDataLiveData
        return data
    }

    fun setOriginAccount(name:String, introduction:String, imageurl: String) //본캐 정보 저장
        = firestore.setOriginAccount(name, introduction, imageurl)

    fun setSubAccount(subaccount_num:Int, name:String, group_name:String, introduction:String, imageurl:String) //부캐 정보 저장
            = firestore.setSubAccount(subaccount_num, name, group_name, introduction, imageurl)

    fun delSubAccount(group_name:String) //부캐 정보 삭제
            = firestore.delSubAccount(group_name)

    fun observeAccountData(): MutableLiveData<List<AccountClass.SubClass>> // 모든 계정 정보 가져오기
    {
        firestore.observeAccountsData()
        var data = firestore.accountsDataLiveData
        return data
    }
    fun observeoneAccountData(group_name: String) : MutableLiveData<AccountClass.SubClass> //group_name 에 해당하는 부캐 계정 정보
    {
        firestore.observeoneAccountData(group_name)
        var data = firestore.accountDataLiveData
        return data
    }
    fun observefollowList():MutableLiveData<List<AccountClass.FollowClass>> //내가 follow하는 list 확인
    {
        firestore.observefollowList()
        var data = firestore.followlistLiveData
        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun follow(toEmail:String) //follow 신청시 상대 Account 의 FollowWaitList 에 들어가게 되는 함수 호출시 Requires API 선언해야함
       =firestore.follow(toEmail)

    fun observefollowerWaitList(): MutableLiveData<List<AccountClass.Follower_wait_list>> // 나에게 온 팔로워 신청 대기 리스트
    {
        firestore.observefollowerWaitList()
        var data = firestore.followerWaitlistLiveData
        return data
    }

    fun modifiedprofile(group_name:String, name:String, introduction:String, imageurl:String) //프로필 수정
        =firestore.modifiedprofile(group_name, name, introduction, imageurl)

    fun acceptfollow(fromEmail:String, group_name_list:List<String>) //팔로우 신청 수락 (fromEmail로 부터 온 팔로우 신청, group_name_list 를 보여주기로 선택)
        =firestore.acceptfollow(fromEmail, group_name_list)

    fun notacceptfollow(fromEmail:String) //팔로우 신청 거절, follow waitlist 로부터 삭제
        =firestore.notacceptfollow(fromEmail)

    fun uploadprofile(filepath:Uri)
    {
        var data = firestore.uploadprofile(filepath)
        System.out.println("url : "+ data.toString())
    }

    fun imageurl(imagename: String)
    {
        firestore.imageurl(imagename)
        System.out.println("imageurl : "+ firestore.profileimageurl)
    }

}