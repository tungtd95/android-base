package com.sekiro.ui.addcity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.sekiro.data.models.City
import com.sekiro.databinding.ActivityAddCityBinding
import com.sekiro.ui.addcity.components.CitiesController
import com.sekiro.ui.base.BaseActivity
import com.sekiro.utils.showKeyboardDelay
import org.koin.android.ext.android.inject

class AddCityActivity : BaseActivity<AddCityViewModel>(), CitiesController.Listener {

    private lateinit var binding: ActivityAddCityBinding
    private val controller: CitiesController by lazy { CitiesController(this) }

    override val viewModel: AddCityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun setupUI() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchCity(text.toString())
        }
        binding.rvCities.setController(controller)
        showKeyboardDelay(binding.etSearch, 250)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.cities.observe(this, {
            controller.cities = it
        })
        viewModel.loading.observe(this, {
            binding.pbSearching.isVisible = it.isLoading
        })
        viewModel.addCityToFavComplete.observe(this, {
            onBackPressed()
        })
    }

    override fun onClickCity(city: City) {
        viewModel.addCityToFav(city)
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, AddCityActivity::class.java)
    }
}