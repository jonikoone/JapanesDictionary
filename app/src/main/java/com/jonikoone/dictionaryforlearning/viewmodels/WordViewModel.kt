package com.jonikoone.dictionaryforlearning.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.entites.Word

class WordViewModel(private val word: Word) : ViewModel() {

    val wordOnyomi: LiveData<String> = MutableLiveData(word.word)
    val wordTranslate: LiveData<String> = MutableLiveData(word.translate)


}