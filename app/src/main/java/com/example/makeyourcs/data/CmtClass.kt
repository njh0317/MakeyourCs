package com.example.makeyourcs.data

data class CmtClass(var post_id:Int? = 0){
    data class CmtInPostClass(var cmt_id:Int? = 0,
                              var cmt_account:String? = null,
                              var message:String? = null,
                              var cmt_date:String? = null,
                              var depth:Int ?= 0,
                              var recmt_num:Int? = 0,
                              var cmt_like:Int? = 0)
}