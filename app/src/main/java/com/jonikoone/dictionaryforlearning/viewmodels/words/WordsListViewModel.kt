package com.jonikoone.dictionaryforlearning.viewmodels.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.ItemWordBinding
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

open class WordsListViewModel(val dictionaryId: Long? = null) : ViewModel(), KoinComponent {

    val database by inject<AppDatabase>()

    val titleDictionary: MutableLiveData<String> = MutableLiveData("All Words")
    val adapter = WordsAdapter()

    init {
        database.getWordDao().getWords().observeForever {
            adapter.updateList(it)
        }
    }

    fun addWord() {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("add Word")
            database.getWordDao().addWord(Word(word = "ことば", translate = "word", caseWord = "言葉"))
        }
    }

}

class WordsAdapter : BaseAdapter<Word>() {
    override fun createDiffCallback(oldList: List<Word>, newList: List<Word>): DiffCallback<Word> =
        object : DiffCallback<Word>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].id == newList[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].word == newList[newItemPosition].word
                        || oldList[oldItemPosition].translate == newList[newItemPosition].translate
                        || oldList[oldItemPosition].caseWord == newList[newItemPosition].caseWord


        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Word> =
        WordViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_word,
                parent,
                false
            )
        )

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        BaseViewHolder<Word>(binding), KoinComponent {
        override fun bind(data: Word) {
            binding.viewModel = get { parametersOf(data) }
        }
    }

}
