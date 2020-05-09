package com.jonikoone.dictionaryforlearning.fragments.words

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentWordsListBinding
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordsListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class WordsListFragment : Fragment() {

    var dictionaryId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            dictionaryId = it.getLong(DICTIONARY_KEY)
        }
        super.onCreate(savedInstanceState)
    }

    private val viewModel: WordsListViewModel by viewModel { parametersOf(dictionaryId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWordsListBinding>(
            inflater,
            R.layout.fragment_words_list,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    companion object {
        const val DICTIONARY_KEY = "com.jonikoone.dictionary.id"
    }

}