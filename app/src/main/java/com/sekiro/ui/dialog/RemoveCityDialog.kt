package com.sekiro.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sekiro.R
import com.sekiro.data.models.City
import com.sekiro.databinding.DialogRemoveCityBinding
import com.sekiro.ui.base.BaseDialogFragment
import com.sekiro.ui.base.DummyViewModel
import org.koin.android.ext.android.inject

class RemoveCityDialog(
    private val listener: Listener,
    private val city: City
) : BaseDialogFragment<DummyViewModel, DialogRemoveCityBinding>() {

    override val viewModel: DummyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRemoveCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        super.setupUI()
        binding.tvRemoveConfirm.text = getString(R.string.remove_city, city.getFullName())
        binding.btnOk.setOnClickListener {
            listener.removeConfirmed()
            dismissAllowingStateLoss()
        }
        binding.btnCancel.setOnClickListener { dismissAllowingStateLoss() }
    }

    interface Listener {
        fun removeConfirmed()
    }
}