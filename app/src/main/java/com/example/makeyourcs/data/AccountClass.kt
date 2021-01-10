package com.example.makeyourcs.data

data class AccountClass(val email:String, val pw:String, val sub_count:Int,
val following_num:Int, val sub:SubClass, val follow:FollowClass, val follow_wait_list:WaitlistClass ) {

}

//data class Site(val url: String, val title: String) {
//    val description = ""
//}