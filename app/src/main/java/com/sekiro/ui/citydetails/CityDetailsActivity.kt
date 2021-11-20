package com.sekiro.ui.citydetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.airbnb.deeplinkdispatch.DeepLink
import com.sekiro.R
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import com.sekiro.databinding.ActivityCityDetailsBinding
import com.sekiro.deeplink.DeepLinkConst
import com.sekiro.ui.base.BaseActivity
import com.sekiro.utils.isDeepLink
import org.koin.android.ext.android.inject

@DeepLink(DeepLinkConst.CITY_WEATHER_DETAIL)
class CityDetailsActivity : BaseActivity<CityDetailsViewModel>() {

    private lateinit var binding: ActivityCityDetailsBinding

    override val viewModel: CityDetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        if (intent.isDeepLink()) {
            val cityId = intent.getStringExtra(DeepLinkConst.PARAM_CITY_ID)
            viewModel.setCityId(cityId?.toIntOrNull())
        } else {
            val city = intent.getParcelableExtra<City>(EXTRA_CITY)
            viewModel.setCity(city)
        }
        viewModel.weather.observe(this, {
            showWeatherData(it)
        })
    }

    override fun setupUI() {}

    private fun showWeatherData(weatherData: Pair<City, Weather>) {
        binding.tvCityName.text = weatherData.first.getFullName()
        weatherData.second.let {
            binding.tvTemperature.text = getString(
                R.string.temperature,
                it.main?.temp?.toInt() ?: 0
            )
            binding.tvHumidity.text = getString(
                R.string.humidity,
                it.main?.humidity?.toInt() ?: 0
            )
            binding.tvPressure.text = getString(
                R.string.pressure,
                it.main?.pressure?.toInt() ?: 0
            )
        }
    }

    companion object {
        private const val EXTRA_CITY = "EXTRA_CITY"

        fun getStartIntent(context: Context, city: City): Intent {
            val intent = Intent(context, CityDetailsActivity::class.java)
            intent.putExtra(EXTRA_CITY, city)
            return intent
        }
    }
}