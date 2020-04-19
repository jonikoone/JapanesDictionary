package com.jonikoone.dictionaryforlearning.di

import androidx.room.Room
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelItemViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "test")
            .build()
    }

    viewModel { (label: Label) -> LabelItemViewModel(label) }

    viewModel { (label: Label) -> LabelViewModel(get<AppDatabase>().getLabelDao(), label) }

    viewModel { LabelListViewModel(get<AppDatabase>().getLabelDao()) }


    viewModel { (dictionaryId: Long?) -> WordsListViewModel(dictionaryId) }

    viewModel { (word: Word) -> WordViewModel(word) }
}