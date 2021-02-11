package com.example.makeyourcs.ui.user.management.dialog


import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentDelSubAccountDialogBinding

class DelSubAccountDialogFragment(context: Context) : DialogFragment() {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val size = Point()
    val display = windowManager.defaultDisplay

    ///////
//    lateinit var delDialogResult: OnDelDialogResult
//    private lateinit var fragment: Fragment
    //////
    lateinit var binding: FragmentDelSubAccountDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_del_sub_account_dialog, container, false)
        var view = binding.root
//        val view = inflater.inflate(R.layout.fragment_del_sub_account_dialog, container, false)

        isCancelable = true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        binding.deletebtn.setOnClickListener {
//            Toast.makeText(context, "delete btn clicked!", Toast.LENGTH_SHORT).show()
            buttonClickListener.onAccountDeleteButtonClicked()
            dismiss()
        }

        binding.notdeletebtn.setOnClickListener {
            dismiss()
        }
//        view.deletebtn.setOnClickListener {
////            viewModel!!.DeleteAccount(view, groupname)
//            Toast.makeText(context, "$groupname 이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
//            dismiss()
//        }

//        view.notdeletebtn.setOnClickListener {
//            dismiss()
//        }

        return view
    }

    override fun onResume() {
        super.onResume()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    public interface OnButtonClickListener{
        fun onAccountDeleteButtonClicked()
    }

    //클릭 이벤트 설정
    public fun setButtonClickListener(buttonClickListener: OnButtonClickListener){
        this.buttonClickListener = buttonClickListener
    }

    //클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener


}

