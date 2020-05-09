package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.ItemDictionaryBinding
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaryFragment.Companion.DICTIONARY_ID
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf


class DictionaryListViewModel(private val dictionaryDao: DictionaryDao) : ViewModel() {

    val adapter = DictionaryListAdapter()

    init {
        dictionaryDao.getDictionaries().observeForever {
            adapter.updateList(it)
        }
    }

    val decoration = { rv: RecyclerView ->
        DividerItemDecoration(rv.context, DividerItemDecoration.VERTICAL)
    }

    fun addDictionary() {
        viewModelScope.launch(Dispatchers.IO) {
            val idDict = dictionaryDao.insert(Dictionary())
            withContext(Dispatchers.Main) {
                NavScreens.navController.navigate(
                        R.id.action_dictionariesList_to_dictionaryEditFragment,
                        bundleOf(DICTIONARY_ID to idDict)
                )
            }
        }
    }

}

class DictionaryListAdapter : BaseAdapter<Dictionary>() {
    override fun createDiffCallback(
            oldList: List<Dictionary>,
            newList: List<Dictionary>
    ) = object : DiffCallback<Dictionary>(oldList, newList) {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition] === newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DictionaryViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_dictionary,
                            parent,
                            false
                    )
            )

    inner class DictionaryViewHolder(private val binding: ItemDictionaryBinding) :
            BaseViewHolder<Dictionary>(binding), KoinComponent {
        override fun bind(data: Dictionary) {
            binding.viewModel = get { parametersOf(data) }
        }
    }

}



