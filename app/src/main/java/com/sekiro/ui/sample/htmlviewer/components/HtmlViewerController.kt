package com.sekiro.ui.sample.htmlviewer.components

import com.airbnb.epoxy.EpoxyController

class HtmlViewerController : EpoxyController() {
    var text: String? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        text?.let {
            htmlViewerItem {
                id(HtmlViewerItem.ID)
                info(it)
            }
        }
    }
}