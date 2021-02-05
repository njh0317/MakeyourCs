package com.example.makeyourcs.ui.auth

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivitySignupSetProfileBinding
import com.example.makeyourcs.utils.startMainActivity
import kotlinx.android.synthetic.main.activity_signup_set_profile.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import petrov.kristiyan.colorpicker.ColorPicker

class Signup_SetProfileActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_set_profile)

        val binding: ActivitySignupSetProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_set_profile)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        colorPickerButtonSetup()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun colorPickerButtonSetup(){
//        var gradientDrawable = GradientDrawable()
//        var drawable = resources.getDrawable(R.drawable.background_colorbox, theme)
//        var drawable = colorbox.background as GradientDrawable

        set_background.setOnClickListener{
            val colorPicker = ColorPicker(this)
            colorPicker.setOnFastChooseColorListener(object : ColorPicker.OnFastChooseColorListener{
                override fun setOnFastChooseColorListener(position: Int, color: Int) {
                    Log.d("color", color.toString())
                    System.out.println(color.toString())
//                    drawable.setColor(color)

                }

                override fun onCancel() {
                    colorPicker.dismissDialog()
                }

            })
                .disableDefaultButtons(true)
                .setColumns(5)
                .setRoundColorButton(true)
                .show()
        }

    }

    override fun onStarted() {
//        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        Toast.makeText(this, "본캐 프로필 설정 완료", Toast.LENGTH_SHORT).show()
        startMainActivity()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}