package com.sekiro.ui.citydetails

import com.sekiro.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class CityDetailsActivity : BaseActivity<CityDetailsViewModel>() {

    override val viewModel: CityDetailsViewModel by inject()

    override fun setupUI() {
    }

}