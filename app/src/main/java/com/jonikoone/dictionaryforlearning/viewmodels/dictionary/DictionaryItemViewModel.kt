package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.fragments.words.WordsListFragment.Companion.DICTIONARY_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class DictionaryItemViewModel(private val dictionaryDao: DictionaryDao, private val dictionary: Dictionary) : ViewModel() {

    //val labelColor = MutableLiveData<Int>()
    val labelVisible = MutableLiveData(View.GONE)
    val titleDictionary = MutableLiveData<String>(dictionary.title)

    fun openDictionary() {
        NavScreens.navController.navigate(R.id.action_dictionariesList_to_wordsListFragment, bundleOf(DICTIONARY_KEY to dictionary.id))
    }

    /*init {
        dictionaryDao.getDictionaryWithLabelItem(idDict).observeForever {
            it.label?.let {label ->
                //labelColor.value = label.color
                labelVisible.value = View.VISIBLE
            }
            titleDictionary.value = it.dictionary.title
        }
    }*/


}