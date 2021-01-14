package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.makeyourcs.data.PostClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class PostingActivity : AppCompatActivity()  {

    var firestore : FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()

//        setButtonClickEvent()
    }
//    private fun setButtonClickEvent(){
//        set_posting_btn.setOnClickListener{onClick(set_posting_btn)}
//        get_posting_btn.setOnClickListener{onClick(get_posting_btn)}
//    }
    private fun onClick(view: View) = View.OnClickListener {
        when(view){
            set_posting_btn -> {
                setPost()
            }
            get_posting_btn -> {
                getPost()
            }
        }

    }
    private fun setPost()
    {
        var posting = PostClass()
        posting.postId = 1 //난수로 시스템에서 아이디생성
        posting.post_account = "sobin"
        posting.content = "life without fxxx coding^^"
        posting.first_pic = "../images/test.jpg"
        posting.place_tag = "homesweethome"
        try{
            firestore?.collection("Post")?.document(posting.postId.toString())?.set(posting)
        }
        catch(e:Exception){
            Log.d("cannot upload", e.toString())
        }

    }
    private fun getPost(postId:String)
    {
        try{
            firestore?.collection("Post")?.document(postId)?.get()?.addOnCompleteListener{task->
                if(task.isSuccessful){
                    val posting = PostClass()
                    posting.postId = task.result!!["postId"].toString().toInt()
                    posting.post_account = task.result!!["post_account"].toString()
                    posting.content = task.result!!["content"].toString()
                    posting.first_pic = task.result!!["first_pic"].toString()

                    System.out.println(posting)
                }

            }
        }catch(e:Exception)
        {
            Log.d("cannot get", e.toString())
        }

    }

}