package com.example.makeyourcs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.alarm.AlarmFragment
import com.example.makeyourcs.ui.home.HomeFragment
import com.example.makeyourcs.ui.home.HomeViewModel
import com.example.makeyourcs.ui.home.HomeViewModelFactory
import com.example.makeyourcs.ui.search.SearchFragment
import com.example.makeyourcs.ui.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity() : AppCompatActivity(),KodeinAware, BottomNavigationView.OnNavigationItemSelectedListener {
    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    val TAG="MAINACTIVITY"
    private lateinit var viewModel:HomeViewModel

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_home ->{
                var homeFragment= HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,homeFragment).commit()

                return true
            }
            R.id.action_search ->{
                var searchFragment= SearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,searchFragment).commit()
                return true
            }
            R.id.action_add_photo ->{

                return true
            }
            R.id.action_favorite_alarm ->{
                var alarmFragment=
                    AlarmFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,alarmFragment).commit()
                return true
            }
            R.id.action_account ->{
                var userFragment= UserFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,userFragment).commit()
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