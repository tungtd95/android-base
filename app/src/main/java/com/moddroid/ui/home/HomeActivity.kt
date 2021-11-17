package com.moddroid.ui.home

import com.moddroid.R
import com.moddroid.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity<HomeViewModel>() {

    override val layoutRes: Int
        get() = R.layout.activity_home

    override val viewModel: HomeViewModel by inject()

    override fun setupUI() {}
}