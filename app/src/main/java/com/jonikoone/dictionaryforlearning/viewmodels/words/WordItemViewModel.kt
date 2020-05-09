package com.jonikoone.dictionaryforlearning.viewmodels.words

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import timber.log.Timber

class WordItemViewModel(private val wordDao: WordDao, private var word: Word) : ViewModel() {

    private var isCheckable = false

    val labelVisible = MutableLiveData(View.VISIBLE)
    val checkableVisible = MutableLiveData(View.INVISIBLE)

    val wordData: LiveData<String> = MutableLiveData(word.word)
    val translate: LiveData<String> = MutableLiveData(word.translate)
    val labelColor: LiveData<Int> = MutableLiveData(0xffff0000.toInt())

    val checkabelListener = View.OnLongClickListener {
        checkItem()
        true
    }

    val checkable = MutableLiveData(isCheckable).apply {
        observeForever {
            if (isCheckable) {
                labelVisible.value = View.INVISIBLE
                checkableVisible.value = View.VISIBLE
            } else {
                labelVisible.value = View.VISIBLE
                checkableVisible.value = View.INVISIBLE
            }
        }
    }

    fun openWord() {
        NavScreens.navController.navigate(R.id.action_wordsListFragment_to_wordFragment)
    }

    fun checkItem() {

        isCheckable = !isCheckable
        checkable.value = isCheckable

        Timber.d("change check: $isCheckable and ${checkable.value}")

    }




}