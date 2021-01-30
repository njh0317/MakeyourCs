package com.example.makeyourcs.ui.user.management

import android.view.View
import android.widget.Toast


class AccountMgtItem (val image: Int, val name: String, val groupname: String){
    fun onClickListener(view: View){
        Toast.makeText(view.context, "Clicked: $groupname", Toast.LENGTH_SHORT).show()
    }
}