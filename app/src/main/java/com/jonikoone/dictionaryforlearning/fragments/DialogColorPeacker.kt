package com.jonikoone.dictionaryforlearning.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.DialogColorPeackerBinding
import com.jonikoone.dictionaryforlearning.viewmodels.ColorPeackerViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialogColorPeacker : DialogFragment() {

    private val colorPeackerViewModel: ColorPeackerViewModel by viewModel { parametersOf((0xff32fafe)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DialogColorPeackerBinding>(inflater, R.layout.dialog_color_peacker, container, false)

        binding.viewModel = colorPeackerViewModel

        binding.lifecycleOwner = this

        return binding.root
    }

}