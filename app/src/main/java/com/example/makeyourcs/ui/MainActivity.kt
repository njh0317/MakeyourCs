package com.example.makeyourcs.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.alarm.AlarmFragment
import com.example.makeyourcs.ui.home.HomeFragment
import com.example.makeyourcs.ui.search.SearchFragment
import com.example.makeyourcs.ui.upload.UploadActivity
import com.example.makeyourcs.ui.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity() : AppCompatActivity(),KodeinAware, BottomNavigationView.OnNavigationItemSelectedListener {
    override val kodein by kodein()
    val TAG="MAINACTIVITY"
    var groupname:String?="본 계정"

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                var homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, homeFragment)
                    .commit()

                return true
            }
            R.id.action_search -> {
                var searchFragment =
                    SearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, searchFragment)
                    .commit()
                return true
            }
            R.id.action_upload -> {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    ==PackageManager.PERMISSION_GRANTED){
                        Log.d("upload","it's in main activity2")
                        startActivity(Intent(this,
                            UploadActivity::class.java))
                }

                return true
            }
            R.id.action_favorite_alarm -> {
                var alarmFragment =
                    AlarmFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, alarmFragment)
                    .commit()
                return true
            }
            R.id.action_account -> {
                var userFragment = UserFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, userFragment)
                    .commit()
                return true
            }
        }
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)

        //Set default screen
        bottom_navigation.selectedItemId= R.id.action_home


    }
    public override fun onStart(){
        super.onStart()
    }
}