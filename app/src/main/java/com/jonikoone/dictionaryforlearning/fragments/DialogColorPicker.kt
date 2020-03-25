package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.databinding.DialogColorPickerBinding
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment
import com.jonikoone.dictionaryforlearning.viewmodels.ColorPickerViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialogColorPicker : Fragment() {

    companion object {
        fun newInstance(label: com.jonikoone.databasemodule.database.entites.Label) = DialogColorPicker().apply {
            arguments = Bundle().apply {
                putSerializable(LabelItemFragment.LABEL_ARG, label)
            }
        }


    }

    private val label by lazy { arguments?.getSerializable(LabelItemFragment.LABEL_ARG) as com.jonikoone.databasemodule.database.entites.Label }

    private val colorPickerViewModel: ColorPickerViewModel by viewModel { parametersOf(label) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DialogColorPickerBinding>(inflater, R.layout.dialog_color_picker, container, false)

        binding.viewModel = colorPickerViewModel

        binding.lifecycleOwner = this

        return binding.root
    }

}