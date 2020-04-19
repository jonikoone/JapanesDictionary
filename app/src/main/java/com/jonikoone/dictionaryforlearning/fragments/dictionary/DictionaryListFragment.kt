package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentDictionaryListBinding
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DictionaryListFragment : Fragment() {

    private val viewModelDictionares: DictionaryListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDictionaryListBinding>(inflater, R.layout.fragment_dictionary_list, container, false)

        //binding.viewModel = viewModelDictionares

        return binding.root
    }

}