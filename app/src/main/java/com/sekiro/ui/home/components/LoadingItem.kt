package com.sekiro.ui.home.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.sekiro.databinding.ItemLoadingBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoadingItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleDef: Int = 0
) : ConstraintLayout(context, attributeSet, styleDef) {

    init {
        ItemLoadingBinding.inflate(LayoutInflater.from(context), this, true)
    }

    companion object {
        const val ID = "LoadingItem"
    }
}