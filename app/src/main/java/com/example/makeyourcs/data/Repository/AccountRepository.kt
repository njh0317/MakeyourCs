package com.example.makeyourcs.data.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.AccountPostClass
import com.example.makeyourcs.data.PlaceClass
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

    fun uploadpostpergroup(group_name_list: List<String>, postId:Int, postDate: Date) //올리려는 게시글을 사용자가 정한 group_name_list에 등록하는 함수
        =firestore.uploadpostpergroup(group_name_list, postId, postDate) //TODO:upload 에 합쳐줘야함
    //TODO:Date 타입 호출할때 다음 코드 참고
    //    val now: Long = System.currentTimeMillis()
    //    val date = Date(now)

    fun observepostpergroup(group_name: String, toEmail: String, option:Int):MutableLiveData<List<AccountPostClass.PostIdClass>>
    //선택된 group 에 해당하는 post 리스트를 보여줍니다.
    //내 피드 게시글의 경우에는 option : 0, toEmail : "default" 로 호출합니다.
    //상대 피드 게시글의 경우에는 option : 1, toEmail : 상대 이메일
    //TODO:POST ID 정보만 리스트로 가져오기 때문에 추후 POST ID에 해당하는 POST CLASS 도 가져와야함
    {
        firestore.observepostpergroup(group_name, toEmail, option)
        var data = firestore.postlist
        return data
    }
//
//    fun getplaceinfo(place_name:String):PlaceClass?
//        = firestore.getplaceinfo(place_name)
//

}