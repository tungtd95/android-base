package com.sekiro.ui.home.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import com.sekiro.databinding.ItemWeatherBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class WeatherItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleDef: Int = 0
) : ConstraintLayout(context, attributeSet, styleDef) {

    var weather: Pair<City, Weather>? = null
        @ModelProp set
    var onRemove: (() -> Unit)? = null
        @CallbackProp set
    var onWeatherSelected: (() -> Unit)? = null
        @CallbackProp set

    private val binding = ItemWeatherBinding.inflate(LayoutInflater.from(context), this, true)

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind() {
        weather?.let {
            binding.tvCityName.text = it.first.getFullName()
            binding.tvTemperature.text = "${it.second.main?.temp?.toInt()} Fahrenheit"
            binding.ivRemove.setOnClickListener {
                onRemove?.invoke()
            }
            binding.root.setOnClickListener {
                onWeatherSelected?.invoke()
            }
        }
    }

    companion object {
        const val ID = "WeatherItem"
    }
}