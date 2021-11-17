package com.sekiro.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    @get:LayoutRes
    abstract val layoutRes: Int

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        setupViewModel()
        setupUI()
    }

    open fun setupViewModel() {}

    abstract fun setupUI()

}
