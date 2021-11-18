package com.sekiro.ui.home

import android.os.Bundle
import com.sekiro.databinding.ActivityHomeBinding
import com.sekiro.ui.addcity.AddCityActivity
import com.sekiro.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity<HomeViewModel>() {

    private lateinit var binding: ActivityHomeBinding

    override val viewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun setupUI() {
        binding.fabAddCity.setOnClickListener {
            startActivity(AddCityActivity.getStartIntent(this))
        }
    }
}