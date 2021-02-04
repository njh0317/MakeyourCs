package com.example.makeyourcs.data.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.PostClass
import com.example.makeyourcs.data.firebase.FirebaseAuthSource
import java.util.*

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

    fun setOriginAccount(name:String, introduction:String, imageurl:String) //본캐 정보 저장
        = firestore.setOriginAccount(name, introduction, imageurl)

    fun setSubAccount(subaccount_num:Int, name:String, group_name:String, introduction:String, imageurl:String) //부캐 정보 저장
            = firestore.setSubAccount(subaccount_num, name, group_name, introduction, imageurl)

    fun delSubAccount(group_name:String) //부캐 정보 삭제
            = firestore.delSubAccount(group_name)

    fun observeAccountData(): MutableLiveData<List<AccountClass.SubClass>> // 계정 정보 가져오기
    {
        firestore.observeAccountData()
        var data = firestore.accountDataLiveData
//        System.out.println("repository : "+data.value)
        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun follow(toEmail:String) //follow 신청시 상대 Account 의 FollowWaitList 에 들어가게 되는 함수 호출시 Requires API 선언해야함
       =firestore.follow(toEmail)

    fun observefollowerWaitList(): MutableLiveData<List<AccountClass.Follower_wait_list>> // 나에게 온 팔로워 신청 대기 리스트
    {
        firestore.observeAccountData()
        var data = firestore.followerWaitlistLiveData
        return data
    }
    fun observePostData(): MutableLiveData<PostClass>
    {
        firestore.observePostData()
        var data = firestore.postDataLiveData
        return data
    }

    fun uploadpostpergroup(group_name_list: List<String>, postId:Int, postDate: Date)
        =firestore.uploadpostpergroup(group_name_list, postId, postDate)
    //TODO:Date 타입 호출할때 다음 코드 참고
    //    val now: Long = System.currentTimeMillis()
    //    val date = Date(now)



}