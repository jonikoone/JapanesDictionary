package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.ItemDictionaryBinding
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryItemViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DictionaryFragment : Fragment() {

  val viewModelDict: DictionaryItemViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ItemDictionaryBinding>(inflater, R.layout.item_dictionary, container, false)
        binding.viewModel = viewModelDict
        binding.lifecycleOwner = this
        return binding.root
    }


}
