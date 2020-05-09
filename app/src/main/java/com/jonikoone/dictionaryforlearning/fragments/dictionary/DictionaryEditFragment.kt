package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryEditViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DictionaryEditFragment : Fragment() {



    val viewModel: DictionaryEditViewModel by viewModel { parametersOf() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}