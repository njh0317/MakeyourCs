package com.example.makeyourcs.ui.user.settings

import android.view.View
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.makeyourcs.R
import com.example.makeyourcs.ui.user.UserViewModel
import com.example.makeyourcs.ui.user.management.AccountMgtMainActivity
import kotlinx.android.synthetic.main.fragment_settingdlg.view.*

class SettingDialogFragment: DialogFragment() {

    private val viewmodel: UserViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_settingdlg, container, false)

        isCancelable=true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        view.account_mgt.setOnClickListener{
            Toast.makeText(context,"계정 관리입니다",Toast.LENGTH_SHORT).show()
            val intent = Intent(context, AccountMgtMainActivity::class.java)
            startActivity(intent)
        }
        view.profile_edit.setOnClickListener{
            Toast.makeText(context,"프로필 편집입니다",Toast.LENGTH_SHORT).show()
        }
        view.archive.setOnClickListener{
            Toast.makeText(context,"아카이브입니다",Toast.LENGTH_SHORT).show()
            val intent= Intent(context, ArchiveActivity::class.java)
            startActivity(intent)
        }
        view.logout.setOnClickListener{
            Toast.makeText(context,"로그아웃입니다",Toast.LENGTH_SHORT).show()
            viewmodel.logout(view)
        }
        view.cancel_button.setOnClickListener {
            dismiss()
        }

        return view
    }

    fun getInstance(): SettingDialogFragment {
        return SettingDialogFragment()
    }
}
