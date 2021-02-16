package com.example.makeyourcs.data

import android.net.Uri

data class PostClass(var postId: String? = null,
                     var account:String? = null,
                     var email:String? = null,
                     var content:String? = null,
                     var like:Int ?= 0,
                     var comment_cnt:Int ?= 0,
                     var imgUrl:Uri? = null,
                     var is_stored:Boolean ?= false,
                     var place_tag:String? = null)
{
    data class LikeClass(var account:MutableList<String>? = ArrayList())
    data class PictureClass(var order:Int? = 0,
                            var picture_url:String? = null){
        data class TagClass(var tagged_id:String? = null,
                            var x_idx:Int? = 0,
                            var y_idx:Int? = 0)
    }

}

