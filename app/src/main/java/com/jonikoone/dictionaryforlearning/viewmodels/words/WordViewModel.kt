package com.jonikoone.dictionaryforlearning.viewmodels.words

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment.Companion.WORD_ARG
import com.jonikoone.dictionaryforlearning.util.SuspendWork
import com.jonikoone.dictionaryforlearning.util.SuspendWorkDefault
import org.koin.core.KoinComponent
import org.koin.core.inject

class WordViewModel(private var word: Word) : ViewModel(), KoinComponent, LifecycleObserver,
    SuspendWork<Word> by SuspendWorkDefault() {

    val database: AppDatabase by inject()

    override val work: (word: Word) -> Unit = {
        database.getWordDao().updateWord(it)
    }

    val wordData = MutableLiveData(word.word).apply {
        observeForever {
            word = word.copy(word = it)
            backgroundWork(word)
        }
    }
    val translate = MutableLiveData(word.translate).apply {
        observeForever {
            word = word.copy(translate = it)
            backgroundWork(word)
        }
    }
    val case = MutableLiveData(word.caseWord).apply {
        observeForever {
            word = word.copy(caseWord = it)
            backgroundWork(word)
        }
    }

    fun openWord() {
        val bundle = Bundle().apply {
            putSerializable(WORD_ARG, word)
        }
        NavScreens.navController.navigate(R.id.wordFragment, bundle)
    }


    val backAction = View.OnClickListener { NavScreens.navController.popBackStack() }
}

