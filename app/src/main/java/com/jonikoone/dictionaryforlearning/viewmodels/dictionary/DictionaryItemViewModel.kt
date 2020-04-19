package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Dictionary
import org.koin.core.KoinComponent
import org.koin.core.inject

class DictionaryItemViewModel(dictionaryId: Long) : ViewModel(), KoinComponent {

    private val database by inject<AppDatabase>()

    private val dict by lazy {
        database.getDictionaryDao().getDictionaryWithLabelItem(dictionaryId).apply {
            observeForever {

            }
        }
    }

    val title = MutableLiveData(dict.value?.dictionary?.title)
    val labelColor = MutableLiveData(dict.value?.label?.color)

}