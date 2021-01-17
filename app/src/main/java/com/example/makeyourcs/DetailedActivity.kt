package com.example.makeyourcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        //        var receiveData1 = intent.getStringExtra("data1")
        //        var receiveData2 = intent.getIntExtra("data2", 0)

        var receiveData1 = intent.getStringExtra("image_name")

        if (receiveData1 != "") {
            val resourceId =
                resources.getIdentifier(receiveData1, "drawable", packageName)

            if (resourceId > 0) {
                imageView1.setImageResource(resourceId)
            } else {
                imageView1.setImageResource(R.mipmap.ic_launcher_round)
            }
        } else {
            imageView1.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}