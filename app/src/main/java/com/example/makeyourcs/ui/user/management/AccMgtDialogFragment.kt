package com.example.makeyourcs.ui.user.management

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentAccMgtDialogBinding


class AccMgtDialogFragment(context: Context) : DialogFragment() {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val size = Point()
    val display = windowManager.defaultDisplay

    lateinit var binding: FragmentAccMgtDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acc_mgt_dialog, container, false)
        var view = binding.root

        isCancelable = true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.deletebtn.setOnClickListener {
            buttonClickListener.onAccountMgtMenu_Delete()
            dismiss()
        }

        return view
    }

    override fun onResume() { // 전체화면의 90프로 너비
        super.onResume()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    public interface OnButtonClickListener{
        fun onAccountMgtMenu_Delete()
        fun onAccountMgtMenu_ChangeFollower()
    }

    //클릭 이벤트 설정
    public fun setButtonClickListener(buttonClickListener: OnButtonClickListener){
        this.buttonClickListener = buttonClickListener
    }

    //클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener
}