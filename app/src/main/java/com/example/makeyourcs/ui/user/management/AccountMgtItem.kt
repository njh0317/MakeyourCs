package com.example.makeyourcs.ui.user.management

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast

class AccountMgtItem (val image: Drawable, val name: String){
    fun onClickListener(view: View){
        Toast.makeText(view.context, "Clicked: $name", Toast.LENGTH_SHORT).show()
    }
}