package com.example.makeyourcs.data.firebase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import android.net.Uri

import androidx.lifecycle.MutableLiveData
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.AccountPostClass
import com.example.makeyourcs.data.PlaceClass
import com.example.makeyourcs.data.PostClass
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.lang.Boolean.FALSE
import java.time.LocalDateTime
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*

class   FirebaseAuthSource {
    val TAG = "FirebaseSource"
    val userDataLiveData = MutableLiveData<AccountClass>()
    val accountDataLiveData = MutableLiveData<List<AccountClass.SubClass>>()
    val followerWaitlistLiveData = MutableLiveData<List<AccountClass.Follower_wait_list>>()
    val postDataLiveData = MutableLiveData<PostClass>()

    val postlist = MutableLiveData<List<AccountPostClass.PostIdClass>>()
    val postlist2 : MutableList<AccountPostClass.PostIdClass> = arrayListOf()

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore: FirebaseFirestore by lazy{
        FirebaseFirestore.getInstance()
    }
    private val firebaseStorage: FirebaseStorage by lazy{
        FirebaseStorage.getInstance()
    }

    fun login(email: String, password: String) = Completable.create { emitter ->
        Log.d("TAG", email)
        firebaseAuth.signInWithEmailAndPassword(email!!, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
//                    System.out.println("login : " + firebaseAuth.currentUser?.email)
                    emitter.onComplete()
                    userIdbyEmail()
                }
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(account: AccountClass) = Completable.create { emitter ->
        System.out.println(account)
        //https://inspirecoding.app/lessons/using-viewmodel/
        firestore.collection("Account").document(account.email.toString()).set(account)

        firebaseAuth.createUserWithEmailAndPassword(account.email.toString(), account.pw.toString()).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    System.out.println("insert success")
                    userIdbyEmail()
                    emitter.onComplete()

                }
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    fun deleteUser() {
        // [START delete_user]
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
        // [END delete_user]
        //TODO: 사용자 부캐안에 게시글 삭제
    }
    fun userIdbyEmail() : String? {
        var userId:String? = null
        firestore.collection("Account")
            .whereEqualTo("email", currentUser()!!.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    userId = document.get("userId").toString()
                    Log.d(TAG, "userId get success "+userId)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return userId
    }

    fun observeUserData() {
        try {
            firestore.collection("Account").whereEqualTo("email", currentUser()!!.email.toString()).addSnapshotListener{ value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    doc?.let {
                        val data = it?.toObject(AccountClass::class.java)
                        System.out.println(data)
                        userDataLiveData.postValue(data)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }

    fun setOriginAccount(name:String, introduction:String, imageurl:String) { //TODO: 사진 url :default
        val OriginAccount = AccountClass.SubClass()
        OriginAccount.name = name
        OriginAccount.introduction = introduction
        OriginAccount.sub_num = 0
        OriginAccount.group_name = "default"
        OriginAccount.profile_pic_url = imageurl

        firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(OriginAccount.group_name.toString())
            .set(OriginAccount)
            .addOnSuccessListener {
                Log.d(TAG, "First subaccount insert complete")
                return@addOnSuccessListener
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e)
                    return@addOnFailureListener
            }
    }
    fun setSubAccount(subaccount_num:Int, name:String, group_name:String, introduction:String, imageurl:String) { //TODO: 사진 url :default
        val SubAccount = AccountClass.SubClass()
        SubAccount.name = name
        SubAccount.introduction = introduction
        SubAccount.sub_num = subaccount_num+1
        SubAccount.group_name = group_name
        SubAccount.profile_pic_url = imageurl

        val subaccount = firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(group_name)
        val account = firestore.collection("Account").document(currentUser()!!.email.toString())
        // Get a new write batch and commit all write operations
        firestore.runBatch { batch ->
            // Set the value of 'NYC'
            batch.set(subaccount, SubAccount)
            // Update the number of sub account
            batch.update(account, "sub_count", subaccount_num+1)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

    }
    fun delSubAccount(group_name:String) {
        val subaccount = firestore
            .collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("SubAccount")
            .document(group_name)
        val account = firestore.collection("Account")
            .document(currentUser()!!.email.toString())

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(account)
            val newSubcount = Integer.parseInt(snapshot.get("sub_count")!!.toString()) - 1
            // Update the number of sub account
            transaction.delete(subaccount)
            transaction.update(account, "sub_account", newSubcount)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    fun observeAccountData() {
        var subaccountlist : ArrayList<AccountClass.SubClass> = arrayListOf()
        try {
            firestore.collection("Account")
                .document(currentUser()!!.email.toString())
                .collection("SubAccount")
                .orderBy("sub_num")
                .addSnapshotListener{ value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    subaccountlist.clear()
                    for (doc in value!!) {
                        doc?.let {
                            val data = it?.toObject(AccountClass.SubClass::class.java)
                            subaccountlist.add(data)
                        }
                    }
                    accountDataLiveData.postValue(subaccountlist)
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }

    //TODO:게시글 파트
    fun uploadPhoto(photoUri: Uri) {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"//photoUri 받아서 뷰 모델에서 이름 설정
        //images를 폴더명으로 하고 있으나 업로드 유저 아이디를 폴더명으로 할 예정
        var storageRef = firebaseStorage.reference.child("images/"+fileName)
//        var tmpid = 1;
//        var firestore = firestore.collection("Post")?.document(tmpid.toString())?.update(
//            mapOf(
//                "picture_url" to storageRef.toString()
//            )
//        );
        //모델에서 다운로드
        storageRef.putFile(photoUri).addOnSuccessListener {
            Log.d(TAG, "Upload photo completed")
        }
    }

    fun setPhoto() {
        var posting = PostClass()
        posting.postId = 1 //난수로 시스템에서 아이디생성
        posting.post_account = "sobinsobin"
        posting.content = "life without fxxx coding^^"
        posting.first_pic = "../images/test.jpg"
        posting.place_tag = "homesweethome"
        try{
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)
        }
        catch (e: Exception){
            Log.d("cannot upload", e.toString())
        }

    }


    fun observePostData() {
        System.out.println("observePostData")
        System.out.println("observePostData2: " + currentUser()!!.email)

        try {
            firestore.collection("Post").whereEqualTo("post_account", currentUser()!!.email.toString()).addSnapshotListener{ value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    doc?.let {
                        val data = it?.toObject(PostClass::class.java)
                        System.out.println(data)
                        postDataLiveData.postValue(data)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)
        }
    }

    fun deletePhoto(){ //추후 delete하는 Activity에 추가
//        FirebaseStorage.getInstance().reference.child("images").child(delete_filename_edittext.text.toString()).delete()

    }


    fun setPost()
    {
        var posting = PostClass()
        posting.postId = 1//난수로 시스템에서 아이디생성
        posting.post_account = "dmlfid1348"
        posting.content = "희루가기시러"
        //posting.first_pic = "../images/test.jpg"
        posting.place_tag = "huiru"
        try{
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)
        }
        catch(e: java.lang.Exception){
            Log.d("cannot upload", e.toString())
        }

    }
    fun getPost(postId:Int)
    {
        try{
            firestore?.collection("Post")?.document(postId.toString())?.get()?.addOnCompleteListener{task->
                if(task.isSuccessful){
                    val posting = PostClass()
                    posting.postId = task.result!!["postId"].toString().toInt()
                    posting.post_account = task.result!!["post_account"].toString()
                    posting.content = task.result!!["content"].toString()
                    posting.first_pic = task.result!!["first_pic"].toString()

                    System.out.println(posting)
                }

            }
        }catch(e: java.lang.Exception)
        {
            Log.d("cannot get", e.toString())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun follow(toEmail:String)
    {
        val from = AccountClass.FollowClass()
        from.to_account = toEmail
        from.to_account_sub = hashMapOf("default" to FALSE)

        val to = AccountClass.Follower_wait_list()
        to.follow_date = LocalDateTime.now()
        to.from_account = currentUser()!!.email.toString()

        val fromAccount = firestore.collection("Account")
            .document(currentUser()!!.email.toString())
            .collection("Follow")
            .document(toEmail)
        val toAccount = firestore.collection("Account")
            .document(toEmail)
            .collection("Follower_wait_list")
            .document(currentUser()!!.email.toString())

        firestore.runBatch { batch ->
            batch.set(toAccount, to)
            batch.set(fromAccount, from)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

    }

    fun observefollowerWaitList()
    {
        try {
            firestore.collection("Account")
                .document(currentUser()!!.email.toString())
                .collection("Follower_wait_list")
                .addSnapshotListener{ value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    for (doc in value!!) {
                        doc?.let {
                            val data = it?.toObject(AccountClass.Follower_wait_list::class.java)
                            System.out.println(data)
                            followerWaitlistLiveData.postValue(listOf(data))
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting follower wait list", e)
        }

    }
    fun acceptfollow(fromEmail:String, group_name_list:List<String>)
    {
//        val fromAccount = firestore.collection("Account")
//            .document(fromEmail)
//            .collection("Follow")
//            .document(currentUser()!!.email.toString())
//        val toAccount_waitlist = firestore.collection("Account")
//            .document(currentUser()!!.email.toString())
//            .collection("Follower_wait_list")
//            .document(fromEmail)
//        var toAccount_default = firestore.collection("Account")
//            .document(currentUser()!!.email.toString())
//            .collection("SubAccount")
//            .document("default")
//
//        firestore.runTransaction { transaction ->
//            transaction.delete(toAccount_waitlist) // waitlist 에서 삭제
//            var default_sub_account = transaction.get(toAccount_default).toObject(AccountClass.SubClass::class.java)
//            default_sub_account?.follower_num = default_sub_account?.follower_num
//            default_sub_account?.follower[fromEmail] = true
//            //follwer_list 에 추가 해야함
//            transaction.update(toAccount_default, "follower_num", newfollower_num)
//            for (group_name in group_name_list){
//                var toAccount = firestore.collection("Account")
//                    .document(currentUser()!!.email.toString())
//                    .collection("SubAccount")
//                    .document(group_name)
//                snapshot = transaction.get(toAccount)
//                newfollower_num = Integer.parseInt(snapshot.get("follower_num")!!.toString()) + 1
//                transaction.update(toAccount, "follower_num", newfollower_num)
//
//            }
//
//        }
//            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }


    }

    fun uploadpostpergroup(group_name_list: List<String>, postId:Int, postDate: Date)
    {
        val post_num_list : ArrayList<Int> = arrayListOf()
        firestore.runTransaction{ transaction ->
            //group 별 게시글 개수 가져오기
            for(group_name in group_name_list)
            {
                var check_post_num = firestore.collection("Account")
                    .document(currentUser()!!.email.toString())
                    .collection("SubAccount")
                    .document(group_name)
                val snapshot = transaction.get(check_post_num)
                val data = snapshot?.toObject(AccountClass.SubClass::class.java)
                if (data != null) {
                    data.post_number?.let { post_num_list.add(it) }
                }
            }
            var check = 0
            for(group_name in group_name_list)
            {
                var post = AccountPostClass.PostIdClass()
                post.order_in_feed = post_num_list[check]+1
                post.post_id = postId
                post.posting_date = postDate

                var AccountPost = firestore.collection("AccountPost")
                    .document(currentUser()!!.email.toString())
                    .collection(group_name)
                    .document(postId.toString())

                transaction.set(AccountPost, post)

                var update_post_num = firestore.collection("Account")
                    .document(currentUser()!!.email.toString())
                    .collection("SubAccount")
                    .document(group_name)
                transaction.update(update_post_num, "post_number", post.order_in_feed)

                check+=1
            }
        }
    }
    fun observepostpergroup(group_name: String, toEmail: String, option : Int)
    {
        var checkEmail = toEmail
        if(option == 0){
            checkEmail = currentUser()!!.email.toString()
        }
        var posts : ArrayList<AccountPostClass.PostIdClass> = arrayListOf()
        try{
            firestore?.collection("AccountPost")
                .document(checkEmail)
                .collection(group_name)
                .orderBy("order_in_feed")
                .addSnapshotListener{ value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    posts.clear()
                    for (doc in value!!) {
                        doc?.let {
                            val data = it?.toObject(AccountPostClass.PostIdClass::class.java)
                            posts.add(data)
                        }
                    }
                    postlist.postValue(posts)
                }

            }catch(e: java.lang.Exception)
            {
                Log.d("cannot get", e.toString())
            }
    }

    fun setplacetag(place: PlaceClass)
    {
        //TODO: 따로 함수를 구현해두지만, setPost 할 떄 해줘야 하기 떄문에 이후에 setPost 함수로 이동 해야함
        val placedb = firestore.collection("Place")
            .document(place.place_name.toString())
        firestore.runBatch { batch ->
            batch.set(placedb,place)
        }
            .addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }

    }
    fun getplaceinfo(place_name:String):PlaceClass?
    {
        return try {
            var placedata: PlaceClass
            val docRef = firestore.collection("Place").document(place_name)
            val document = Tasks.await(docRef.get())

            if(document.exists())
            {
                val place = document.toObject(PlaceClass::class.java)
                Log.d(TAG,"place : "+place.toString())
                place
            }else {
                Log.w(TAG, "no data in place")
                null
            }

        } catch(e:Throwable)
        {
            Log.w(TAG, "error in get place, ", e)
            null
        }
    }
}