package com.example.makeyourcs.data

import android.net.Uri
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

data class AccountClass(var userId:String? = null,
                        var email:String? = null,
                        var pw:String? = null,
                        var sub_count:Int? = 0,
                        var following_num:Int? = 0,
                        var year:Int? = 2021,
                        var month:Int? = 1,
                        var day:Int? = 1)
{
    data class SubClass(var sub_num:Int? = 0,
                        var name:String? = null,
                        var introduction:String? = null,
                        var post_number:Int? = 0,
                        var follower_num:Int? = 0,
                        var back_color:String? = "#ffffff",
                        var profile_pic_url: String? = null,
                        var group_name:String? = "",
                        var follower:HashMap<String, Boolean> = HashMap()
    )
    data class FollowClass(var to_account:String? = null,
                           var to_account_sub:Map<String, Boolean> = HashMap()
    )
    data class Follower_wait_list(var from_account:String? = null,
                           var follow_date: LocalDateTime? = null

    )
}
