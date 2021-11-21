package com.sekiro.ui.htmlviewer.components

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.github.chuross.library.ExpandableLayout
import com.github.chuross.library.OnExpandListener
import com.sekiro.R
import com.sekiro.databinding.ItemHtmlViewerBinding
import com.sekiro.utils.GlideImageGetter

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = false)
class HtmlViewerItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleDef: Int = 0
) : ConstraintLayout(context, attributeSet, styleDef) {

    var info: String? = null
        @ModelProp set

    private val binding = ItemHtmlViewerBinding.inflate(LayoutInflater.from(context), this, true)

    @AfterPropsSet
    fun bind() {
        val data = info.orEmpty().replace("http://", "https://")
        binding.tvContent.text =
            Html.fromHtml(data, GlideImageGetter(binding.tvContent), null)
        binding.clSeeMore.setOnClickListener {
            binding.elRoot.toggle()
        }
        binding.elRoot.setOnExpandListener(object : OnExpandListener {
            override fun onExpanded(p0: ExpandableLayout?) {
                binding.tvSeeMore.text = context.getString(R.string.see_less)
                binding.ivDropDown.setImageResource(R.drawable.ic_round_keyboard_arrow_up_24)
            }

            override fun onCollapsed(p0: ExpandableLayout?) {
                binding.tvSeeMore.text = context.getString(R.string.see_more)
                binding.ivDropDown.setImageResource(R.drawable.ic_round_keyboard_arrow_down_24)
            }
        })
        post {
            val isShortText =
                binding.tvContent.height < context.resources.getDimension(R.dimen.collapse_height)
            if (isShortText) {
                postDelayed({
                    try {
                        binding.elRoot.expand()
                    } catch (e: Exception) {
                        // ignore
                    }
                }, 100)
            }
            binding.clSeeMore.isVisible = !isShortText
        }
    }

    companion object {
        const val ID = "HtmlViewerItem"
    }
}