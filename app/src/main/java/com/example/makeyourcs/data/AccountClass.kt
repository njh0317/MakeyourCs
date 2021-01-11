package com.example.makeyourcs.data

data class AccountClass(val userId:String? = null,
                        val email:String? = null,
                        val pw:String? = null,
                        val sub_count:Int? = 0,
                        val following_num:Int? = 0) {
    data class SubClass(val sub_num:Int? = 0,
                        val name:String? = null,
                        val introduction:String? = null,
                        val post_number:Int? = 0,
                        val following_num:Int? = 0,
                        val back_color:String? = "#ffffff",
                        val profile_pic_url:String? = "default_url",
                        val group_name:String? = null,
                        val follower:Map<String, Boolean> = HashMap())
    data class FollowClass(val to_account:String? = null,
                           val to_account_sub:Map<Int, Boolean> = HashMap()

    )
}
