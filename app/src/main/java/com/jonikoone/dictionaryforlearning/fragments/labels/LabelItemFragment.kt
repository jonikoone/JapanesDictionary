package com.jonikoone.dictionaryforlearning.fragments.labels

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Label
import com.jonikoone.dictionaryforlearning.databinding.FragmentLabelBinding
import com.jonikoone.dictionaryforlearning.databinding.ItemLabelBinding
import com.jonikoone.dictionaryforlearning.util.BaseFragment
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class LabelItemFragment : BaseFragment() {

    companion object {
        fun newInstance(labelId: Long) = LabelItemFragment()
            .apply {
            arguments = Bundle().apply {
                putLong(LABEL_ARG, labelId)
            }
        }

        const val LABEL_ARG = "jonikoone.label_item_fragment.label"
    }

    private val labelId: Label by lazy { arguments?.get(LABEL_ARG) as Label }

    private val labelViewModel: LabelViewModel by viewModel { parametersOf(labelId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = createViewDataBinding<FragmentLabelBinding>(inflater, container, R.layout.fragment_label)
        binding.viewModel = labelViewModel
        return binding.root
    }

}