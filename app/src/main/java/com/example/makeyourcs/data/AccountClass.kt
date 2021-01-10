package com.example.makeyourcs.data

data class AccountClass(val email:String, val pw:String, val sub_count:Int,
val following_num:Int, val sub:Array<SubClass>, val follow:Array<FollowClass>, val follow_wait_list:Array<WaitlistClass> ) {
}
