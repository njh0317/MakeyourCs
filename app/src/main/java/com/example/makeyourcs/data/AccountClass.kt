package com.example.makeyourcs.data

data class AccountClass(var userId:String? = null,
                        var email:String? = null,
                        var pw:String? = null,
                        var sub_count:Int? = 0,
                        var following_num:Int? = 0) {
    data class SubClass(var sub_num:Int? = 0,
                        var name:String? = null,
                        var introduction:String? = null,
                        var post_number:Int? = 0,
                        var following_num:Int? = 0,
                        var back_color:String? = "#ffffff",
                        var profile_pic_url:String? = "default_url",
                        var group_name:String? = null,
                        var follower:Map<String, Boolean> = HashMap())
    data class FollowClass(var to_account:String? = null,
                           var to_account_sub:Map<Int, Boolean> = HashMap()

    )
}
