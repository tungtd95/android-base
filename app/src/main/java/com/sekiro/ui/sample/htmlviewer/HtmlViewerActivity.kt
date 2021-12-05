package com.sekiro.ui.sample.htmlviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sekiro.databinding.ActivityHtmlViewerBinding
import com.sekiro.ui.base.BaseActivity
import com.sekiro.ui.sample.htmlviewer.components.HtmlViewerController
import org.koin.android.ext.android.inject

class HtmlViewerActivity : BaseActivity<HtmlViewerViewModel>() {

    private lateinit var binding: ActivityHtmlViewerBinding
    private val controller: HtmlViewerController by lazy { HtmlViewerController() }

    override val viewModel: HtmlViewerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHtmlViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun setupUI() {
        binding.rvContents.setController(controller)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.text.observe(this, {
            controller.text = it
        })
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, HtmlViewerActivity::class.java)
    }
}