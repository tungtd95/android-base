package com.sekiro.ui.home

import android.os.Bundle
import android.widget.Toast
import com.airbnb.deeplinkdispatch.DeepLink
import com.sekiro.data.models.City
import com.sekiro.databinding.ActivityHomeBinding
import com.sekiro.deeplink.DeepLinkConst
import com.sekiro.ui.addcity.AddCityActivity
import com.sekiro.ui.base.BaseActivity
import com.sekiro.ui.citydetails.CityDetailsActivity
import com.sekiro.ui.dialog.RemoveCityDialog
import com.sekiro.ui.home.components.WeatherController
import com.sekiro.ui.splash.SplashDialog
import org.koin.android.ext.android.inject

@DeepLink(DeepLinkConst.HOME)
class HomeActivity : BaseActivity<HomeViewModel>(), WeatherController.Listener {

    private lateinit var binding: ActivityHomeBinding
    private val controller: WeatherController by lazy { WeatherController(this) }

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
        binding.rvWeathers.setController(controller)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getWeathers()
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.weathers.observe(this, {
            controller.weathers = it
            binding.swipeRefreshLayout.isRefreshing = false
        })
        viewModel.loading.observe(this, {
            controller.loading = it.isLoading
            if (!it.isLoading) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        if (viewModel.isFirstTimeStartup()) {
            SplashDialog().show(supportFragmentManager)
            viewModel.markFirstTimeStartup()
        }
    }

    override fun onWeatherSelected(city: City) {
        startActivity(CityDetailsActivity.getStartIntent(this, city))
    }

    override fun onRemove(city: City) {
        RemoveCityDialog(object : RemoveCityDialog.Listener {
            override fun removeConfirmed() {
                viewModel.removeCity(city)
            }
        }, city).show(supportFragmentManager)
    }
}