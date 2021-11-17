package com.sekiro.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sekiro.R

abstract class BaseDialogFragment<VM : BaseViewModel> : DialogFragment() {

    private val tagName: String = this.javaClass.simpleName

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract val viewModel: VM

    abstract fun setupUI()

    abstract fun setupViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        setupUI()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.BOTTOM)
        val windowParams = dialog?.window?.attributes
        windowParams?.dimAmount = 0f
        windowParams?.flags?.let {
            windowParams.flags = it or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        }
        dialog?.window?.attributes = windowParams
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Design_BottomSheetDialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, tagName)
    }
}
