package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentDictionaryBinding
//import com.jonikoone.dictionaryforlearning.databinding.FragmentDictionaryBinding
import com.jonikoone.dictionaryforlearning.viewmodels.DictionaryViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.Koin
import org.koin.core.KoinComponent

class DictionaryFragment : Fragment() {

    val viewModelDict: DictionaryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_dictionary, container, false)

        val binding = DataBindingUtil.inflate<FragmentDictionaryBinding>(inflater, R.layout.fragment_dictionary, container, false)
        binding.viewModel = viewModelDict
        binding.lifecycleOwner = this

        return binding.root
    }

}