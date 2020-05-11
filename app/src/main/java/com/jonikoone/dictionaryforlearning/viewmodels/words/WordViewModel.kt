package com.jonikoone.dictionaryforlearning.viewmodels.words

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.util.SuspendWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class WordViewModel(private val wordDao: WordDao, private var word: Word) : ViewModel(),
        KoinComponent, LifecycleObserver,
        SuspendWork<Word> {

    val router: Router by inject()

    override var job: Job? = null
    override val delaySuspend: Long = 300

    override val work: (word: Word) -> Unit = {
        wordDao.update(word)
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

    fun isEmptyWord(): Boolean =
            wordData.value?.isEmpty() ?: true
                    && translate.value?.isEmpty() ?: true
                    && case.value?.isEmpty() ?: true

    val backAction = View.OnClickListener {
        viewModelScope.launch {
            if (isEmptyWord()) {
                withContext(Dispatchers.IO) {
                    wordDao.delete(word)
                }
            }
            router.exit()
        }

    }

}

