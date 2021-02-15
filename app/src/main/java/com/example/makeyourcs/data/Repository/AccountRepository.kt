package com.example.makeyourcs.data.Repository

import android.net.Uri
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

    fun setOriginAccount(name:String, introduction:String, filepath: String) //본캐 정보 저장
        = firestore.setOriginAccount(name, introduction, filepath)
    //TODO:filepath의 경우 선택된 이미지의 경로인데 꼭 .toString()을 붙여 string 형식으로 바꿔서 호출해줘야합니다.
    // default 사진을 사용할 경우 "default" 로 호출하면 됩니다.

    fun setSubAccount(subaccount_num:Int, name:String, group_name:String, introduction:String, imageurl:String) //부캐 정보 저장
            = firestore.setSubAccount(subaccount_num, name, group_name, introduction, imageurl)

    fun getAllfollower(): MutableLiveData<List<String>> { //나의 전체 팔로워 호출
        firestore.getAllfollower()
        var data = firestore.allfollowerlistLiveData
        return data
    }
    fun setSubaccountFollower(group_name: String, follower_list: List<String>) //Subaccount의 공개범위, 즉 follower 수정
        = firestore.setSubaccountFollower(group_name, follower_list) //파라미터로는 모든 follower의 계정을 리스트로 가져가야함
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

    suspend fun imageurl(imagename: String):Uri? //imagename 에 해당하는 imageurl 을 호출하는 함수
        = firestore.imageurl(imagename)
    //TODO: 호출시 다음과 같이 해야함
    //꼭 GlobalScope.launch 안에 해당 함수를 불러야합니다.!!
    //        GlobalScope.launch {
    //            repository.imageurl("IMAGE_20210203_220411_.png")
    //        }

    fun searchaccount(keyword: String): MutableLiveData<List<String>> //keyword 와 비슷한 account를 livedata 로 값을 가져옴
    {
        firestore.searchaccount(keyword)
        var data = firestore.searchaccountLiveData
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

    suspend fun getplaceinfo(place_name:String):PlaceClass?
        = firestore.getplaceinfo(place_name)
    //TODO: 함수 호출시 다음과 같이 해야함
    //coroutine 방식으로, 비동기적으로 호출하기 때문에 GlobalScope.launch 안에 해당 함수를 호출한다.
    //    GlobalScope.launch {
    //        val owner = repository.getplaceinfo("희루")
    //    }

//    fun setPost(account:String, email:String, content:String, place_tag:String, pAdd: Uri)
//        =firestore.setPost(account, email, content, place_tag, pAdd)

    fun getMyPost(): MutableLiveData<List<PostClass>> // 유저 게시글 정보 가져오기
    {
        firestore.getMyPost()
        var data = firestore.postDataLiveData
        System.out.println("in repository"+ data.value)
        return data
    }
}