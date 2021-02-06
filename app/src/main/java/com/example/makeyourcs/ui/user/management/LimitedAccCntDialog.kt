package com.example.makeyourcs.ui.user.management

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import com.example.makeyourcs.R

class LimitedAccCntDialog(context: Context) {

    private val dialog = Dialog(context);

    fun WarningConfirm(){
        dialog.setContentView(R.layout.limited_subacccnt_dialog)
        // Dialog 배경 투명하게
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // Dialog 크기 설정
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val btn = dialog.findViewById<Button>(R.id.warning_confirm)
        btn.setOnClickListener {
            dialog.dismiss()
        }
    }

//    fun NotDelete(){
//        dialog.dismiss()
//    }
}

interface ButtonClickListener{
    fun onClicked(myName: String)
}

private lateinit var onClickedListener: ButtonClickListener
fun setOnClickedListener(listener: ButtonClickListener){
    onClickedListener = listener
}