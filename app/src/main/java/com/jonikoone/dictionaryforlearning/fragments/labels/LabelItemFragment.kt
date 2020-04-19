package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.databinding.FragmentLabelBinding
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LabelItemFragment : Fragment() {

    companion object {
        const val LABEL = "jonikoone.label_fragment.label"
    }

    private val label: Label by lazy { arguments?.get(LABEL) as Label }

    private val labelViewModel: LabelViewModel by viewModel { parametersOf(label) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLabelBinding>(inflater, R.layout.fragment_label, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = labelViewModel
        return binding.root
    }



}

