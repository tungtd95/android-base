package com.sekiro.ui.sample.addcity.components

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
import com.sekiro.databinding.ItemCityBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CityItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleDef: Int = 0
) : ConstraintLayout(context, attributeSet, styleDef) {

    private val binding = ItemCityBinding.inflate(LayoutInflater.from(context), this, true)

    var city: City? = null
        @ModelProp set
    var onClickCityListener: (() -> Unit)? = null
        @CallbackProp set

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind() {
        city?.let {
            binding.tvCityName.text = it.getFullName()
            binding.root.setOnClickListener { onClickCityListener?.invoke() }
        }
    }

    companion object {
        const val ID = "CityItem"
    }
}