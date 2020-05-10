package com.jonikoone.dictionaryforlearning.di

import androidx.room.Room
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.viewmodels.common.MainFragmentViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryItemViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryListViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.dictionary.DictionaryEditViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelItemViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordItemViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    //database
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "test")
            .build()
    }
    single { get<AppDatabase>().getLabelDao() }
    single { get<AppDatabase>().getWordDao() }
    single { get<AppDatabase>().getDictionaryDao() }

    //Navigation
    val cicerone = Cicerone.create()
    single { cicerone.router }
    single { cicerone.navigatorHolder }


    //view models
    //main fragment
    viewModel { MainFragmentViewModel() }

    //label
    viewModel { (label: Label) -> LabelItemViewModel(label) }
    viewModel { (label: Label) -> LabelViewModel(get(), label) }
    viewModel { LabelListViewModel(get()) }

    //word
    viewModel { (word: Word) -> WordViewModel(get(), word) }
    viewModel { (word: Word) -> WordItemViewModel(get(), word) }
    viewModel { (dictionaryId: Long?) -> WordsListViewModel(get(), dictionaryId) }

    //dictionary
    viewModel { DictionaryListViewModel(get()) }
    viewModel { (dictionary: Dictionary) -> DictionaryItemViewModel(get(), dictionary) }
    viewModel { (dictionary: Dictionary) -> DictionaryEditViewModel(get(), dictionary) }

}