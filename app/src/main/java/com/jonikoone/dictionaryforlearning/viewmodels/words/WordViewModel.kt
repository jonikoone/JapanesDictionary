package com.jonikoone.dictionaryforlearning.viewmodels.words

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment.Companion.WORD_ARG
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class WordViewModel(private var word: Word) : ViewModel(), KoinComponent, LifecycleObserver,
    BackgroundSave<Word> {

    val database: AppDatabase by inject()

    override var job: Job? = null
    override val delaySave: Long = 300
    override val saveBlock: (word: Word) -> Unit = {
        database.getWordDao().updateWord(it)
    }

    val wordData = MutableLiveData(word.word).apply {
        observeForever {
            word = word.copy(word = it)
            backgroundSave(word)
        }
    }
    val translate = MutableLiveData(word.translate).apply {
        observeForever {
            word = word.copy(translate = it)
            backgroundSave(word)
        }
    }
    val case = MutableLiveData(word.caseWord).apply {
        observeForever {
            word = word.copy(caseWord = it)
            backgroundSave(word)
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

interface BackgroundSave<T> : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    var job: Job?
    val delaySave: Long
    val saveBlock: (T) -> Unit

    fun createJob(t: T) = launch {
        delay(delaySave)
        saveBlock(t)
    }

    fun backgroundSave(t: T) {
        job?.cancel()
        job = createJob(t)
    }
}