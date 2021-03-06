package com.example.makeyourcs.data

import java.util.*
import kotlin.collections.ArrayList

data class AccountPostClass(var account_id:String? = null)
{
    data class SubClass(var sub_id:String? = null,
                        var postlist:MutableList<PostIdClass>?=ArrayList()
    )
    data class PostIdClass(var post_id:Int? = null,
                           var order_in_feed:Int? = 0,
                           var posting_date: Date?=null
    )
}