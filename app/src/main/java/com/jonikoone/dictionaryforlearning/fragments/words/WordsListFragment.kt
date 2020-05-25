package com.jonikoone.dictionaryforlearning.fragments.words

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.terrakok.cicerone.Router

class WordsListFragment() : BaseMvpFragment(), WordsListView {
    companion object {
        val ARG_DICTIONARY_ID = "${this::class.qualifiedName}::dictionary_id"
        fun newInstance(dictionaryId: Long) = WordsListFragment().apply {
            arguments = bundleOf(ARG_DICTIONARY_ID to dictionaryId)
        }
    }


    override fun updateList(newList: List<Word>) {

    }

}

@StateStrategyType(AddToEndSingleStrategy::class)
interface WordsListView : MvpView {
    fun updateList(newList: List<Word>)
}

@InjectViewState
class WordsListPresenter(val wordDao: WordDao, val router: Router, val dictionaryId: Long)
    :MvpPresenter<WordsListView>() {

    val words: LiveData<List<Word>> = MutableLiveData<List<Word>>().apply {
        observeForever {
            viewState.updateList(it)
        }
    }


}