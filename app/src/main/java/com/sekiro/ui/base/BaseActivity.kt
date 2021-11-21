package com.sekiro.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    open val shouldShowAd: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupUI()
    }

    open fun setupViewModel() {
        viewModel.networkError.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    abstract fun setupUI()

}
