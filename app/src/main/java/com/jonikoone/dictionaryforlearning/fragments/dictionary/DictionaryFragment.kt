package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentDictionaryBinding
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordsListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class DictionaryFragment : Fragment() {

    var idDict by Delegates.notNull<Long>()

    init {
        arguments?.let {
            idDict = it.getLong(DICTIONARY_ID)
        }
    }

    val viewModelDict: WordsListViewModel by viewModel{ parametersOf(idDict) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentDictionaryBinding>(inflater, R.layout.fragment_dictionary , container, false)
        binding.viewModel = viewModelDict
        binding.lifecycleOwner = this
        return binding.root
    }

    companion object {
        const val DICTIONARY_ID = "com.jonikoone.dictionary.id"
    }

}
