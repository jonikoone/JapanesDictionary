package com.jonikoone.dictionaryforlearning.fragments.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.util.BaseFragment
import org.koin.core.parameter.parametersOf

class WordItem private constructor() : BaseFragment() {

   /* companion object {
        fun newInstance(word: Word) = WordItem().apply {
            arguments = Bundle().apply {
                putSerializable("wordInstance", word)
            }
        }
    }

    private val word: Word = arguments?.get("wordInstance") as Word

    val viewModel: ItemWordViewModel by viewModel { parametersOf(word)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = createViewDataBinding<ItemWordBinding>(inflater, container, R.layout.item_word)

        binding.viewModel = viewModel

        return binding.root
    }*/

}