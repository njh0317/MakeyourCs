package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.data.FollowClass
import com.example.makeyourcs.data.SubClass
import com.example.makeyourcs.data.WaitlistClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val sub_class = SubClass(0, "jihae", "안녕하세요", 1,2,"white","www.url.com",
//            "friend", arrayOf("jihae","sobin"))
//        val follow_class = FollowClass("jihae",arrayOf(1,2))
//        val follow_wait_class = WaitlistClass("sobin","19-11-10")
//
//        val Account = AccountClass("njnjh0317","password",0, 0, arrayOf(sub_class), arrayOf(follow_class), arrayOf(follow_wait_class))
//        print("print"+Account)

    }
}