package com.example.makeyourcs.ui.user

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.makeyourcs.R

class SettingDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // return super.onCreateView(inflater, container, savedInstanceState)

        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.fragment_settingdlg,null)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return activity!!.layoutInflater.inflate(R.layout.fragment_settingdlg, container)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
      //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}