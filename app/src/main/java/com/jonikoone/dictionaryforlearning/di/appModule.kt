package com.jonikoone.dictionaryforlearning.di

import androidx.room.Room
import com.jonikoone.databasemodule.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
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
    single { get<AppDatabase>().getDictionaryWithLabelDao() }

    //Navigation
    val cicerone = Cicerone.create()
    single { cicerone.router }
    single { cicerone.navigatorHolder }

    /*//view models
    //main fragment
    viewModel { MainFragmentViewModel() }

    //label
    viewModel { (label: Label) -> LabelItemViewModel(label) }
    viewModel { (label: Label) -> LabelViewModel(get(), label) }

    //word
    viewModel { (word: Word) -> WordViewModel(get(), word) }
    viewModel { (word: Word) -> WordItemViewModel(get(), word) }
    viewModel { (dictionary: Dictionary?) -> WordsListViewModel(get(), dictionary) }

    //dictionary
    viewModel { DictionaryListViewModel(get()) }
    viewModel { (dictionary: Dictionary) -> DictionaryItemViewModel(get(), dictionary) }
    viewModel { (dictionary: Dictionary) -> DictionaryEditViewModel(get(), dictionary) }*/

}