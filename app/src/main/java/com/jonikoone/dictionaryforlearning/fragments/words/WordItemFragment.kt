package com.jonikoone.dictionaryforlearning.fragments.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.databinding.FragmentWordBinding
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WordItemFragment(private val word: Word) : Fragment() {

    private val wordViewModel by viewModel<WordViewModel> { parametersOf(word) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWordBinding>(inflater, R.layout.fragment_word, container, false)
        binding.lifecycleOwner = this
        lifecycle.addObserver(wordViewModel)
        binding.viewModel = wordViewModel
        return binding.root
    }

}