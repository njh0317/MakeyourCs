package com.example.makeyourcs.data

data class AccountPostClass(var account_id:String? = null)
{
    data class SubClass(var sub_id:String? = null,
                        var postlist:MutableList<PostIdClass>?=ArrayList()

    )
    data class PostIdClass(var post_id:String? = null,
                           var order_in_feed:Int? = 0,
                           var posting_date:String?=null
    )
}