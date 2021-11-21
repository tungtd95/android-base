package com.sekiro.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {

    var _binding: VB? = null
    val binding get() = _binding!!

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupUI()
    }

    open fun setupViewModel() {}

    abstract fun setupObserver()

    abstract fun setupUI()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
