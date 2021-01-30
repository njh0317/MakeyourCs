package com.example.makeyourcs.data

data class PostClass(var postId: Int? = 0,
                     var post_account:String? = null,
                     var post_email:String? = null,
                     var content:String? = null,
                     var like:MutableList<String>? = ArrayList(),
                     var comment_cnt:Int? = 0,
                     var first_pic:String? = null,
                     var is_stored:Boolean? = false,
                     var place_tag:String? = null,
                     var post_data:String? = null)
{
    data class PictureClass(var order:Int? = 0,
                            var picture_url:String? = null){
        data class TagClass(var tagged_id:String? = null,
                            var x_idx:Int? = 0,
                            var y_idx:Int? = 0)
        }
}