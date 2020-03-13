package com.jonikoone.dictionaryforlearning.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.entites.Word

class ItemFragmentViewModel(private val word: Word) : ViewModel() {

    val wordWord: LiveData<String> = MutableLiveData(word.word)
    val wordTranslate: LiveData<String> = MutableLiveData(word.translate)


}