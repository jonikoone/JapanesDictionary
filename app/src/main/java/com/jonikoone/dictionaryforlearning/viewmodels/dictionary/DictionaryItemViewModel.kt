package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import org.koin.core.KoinComponent
import org.koin.core.inject

class DictionaryItemViewModel(val dictionaryDao: DictionaryDao, val dictionary: Dictionary) : ViewModel() {

    val labelColor = MutableLiveData(0)
    val labelVisible = MutableLiveData(View.GONE)
    val titleDictionary = MutableLiveData(dictionary.title)

    init {
        if (dictionary.labelId != 0L)
            dictionaryDao.getDictionaryWithLabelItem(dictionary.id).observeForever {
                labelColor.value = it.label.color
                labelVisible.value = View.VISIBLE
            }
    }


}