package com.example.makeyourcs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.makeyourcs.R
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        if (intent.hasExtra("image_name")) {
            var resourceId = intent.getIntExtra("image_name",0)
            if (resourceId > 0)
                    imageView.setImageResource(resourceId)
            else
                imageView.setImageResource(R.mipmap.ic_launcher_round)
        }
        else
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()

    }
}