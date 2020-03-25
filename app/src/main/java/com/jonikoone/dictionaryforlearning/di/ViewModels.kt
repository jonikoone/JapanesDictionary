package com.jonikoone.dictionaryforlearning.di

import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.viewmodels.ColorPickerViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordsListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModules = module {

    viewModel { (label: Label) -> ColorPickerViewModel(label) }


    viewModel { (label: Label) -> LabelViewModel(label) }
    viewModel { LabelListViewModel() }


    viewModel { (dictionaryId: Long?) -> WordsListViewModel(dictionaryId) }

    viewModel { (word: Word) -> WordViewModel(word)}


}