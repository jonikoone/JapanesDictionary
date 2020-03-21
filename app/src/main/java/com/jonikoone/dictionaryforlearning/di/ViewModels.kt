package com.jonikoone.dictionaryforlearning.di

import com.jonikoone.dictionaryforlearning.database.entites.Label
import com.jonikoone.dictionaryforlearning.viewmodels.ColorPeackerViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

//    viewModel { DictionaryViewModel() }

//    viewModel { LabelsViewModel() }
    viewModel { (label: Label) -> LabelViewModel(label) }
    viewModel { LabelListViewModel() }

    viewModel { (color: Int) -> ColorPeackerViewModel(color) }

}