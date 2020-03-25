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
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf

class LabelItemFragment : Fragment(), KoinComponent {

    companion object {
        const val LABEL_ARG = "jonikoone.label_item_fragment.label"
    }

    private val labelId: Label by lazy { arguments?.get(LABEL_ARG) as Label }

    private val labelViewModel: LabelViewModel by viewModel { parametersOf(labelId) }

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

