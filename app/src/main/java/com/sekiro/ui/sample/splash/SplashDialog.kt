package com.sekiro.ui.sample.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sekiro.databinding.DialogSplashBinding
import com.sekiro.ui.base.BaseDialogFragment
import com.sekiro.ui.base.DummyViewModel
import org.koin.android.ext.android.inject

class SplashDialog : BaseDialogFragment<DummyViewModel, DialogSplashBinding>() {

    override val viewModel: DummyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        isCancelable = false
        binding.ivClose.setOnClickListener { dismissAllowingStateLoss() }
    }
}