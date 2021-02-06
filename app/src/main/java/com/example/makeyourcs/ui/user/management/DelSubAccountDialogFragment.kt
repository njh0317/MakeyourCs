package com.example.makeyourcs.ui.user.management


import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.makeyourcs.R
import kotlinx.android.synthetic.main.fragment_del_sub_account_dialog.view.*

class DelSubAccountDialogFragment(context: Context, private var groupname: String) : DialogFragment(), View.OnClickListener {
//    var viewModel : UserMgtViewModel? = null
    val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val size = Point()
    val display = windowManager.defaultDisplay

    public lateinit var delDialogResult: OnDelDialogResult
    private lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_del_sub_account_dialog, container, false)
//        viewModel = ViewModelProvider(this).get(UserMgtViewModel::class.java)

        isCancelable=true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        view.deletebtn.setOnClickListener{
//            viewModel!!.DeleteAccount(view, groupname)
            Toast.makeText(context, "$groupname 이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        view.notdeletebtn.setOnClickListener {
            dismiss()
        }

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
    fun getInstance(groupname: String): DelSubAccountDialogFragment{
        return DelSubAccountDialogFragment(context!!, groupname)
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.deletebtn){
            if(fragment != null){
                if(delDialogResult != null){
                    delDialogResult.finish(groupname)
                }

                var dialogFragment = fragment as DialogFragment
                dialogFragment.dismiss()
            }

        }
    }

    fun setDialogResult(dialogResult: OnDelDialogResult){
        delDialogResult = dialogResult
    }

    interface OnDelDialogResult{
        fun finish(result: String)
    }
}

