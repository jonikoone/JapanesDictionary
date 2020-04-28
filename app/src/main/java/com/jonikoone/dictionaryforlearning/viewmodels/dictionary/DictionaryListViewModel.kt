package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.ItemDictionaryBinding
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import com.jonikoone.dictionaryforlearning.util.SuspendWork
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class DictionaryListViewModel(val dictionaryDao: DictionaryDao) : ViewModel() {
    //получаем список словарей
    val titleDictionary = "void"
    val adapter = DictionaryListAdapter()

    init {
        dictionaryDao.getDictionaries().observeForever {
            adapter.updateList(it)
        }
    }

    fun addDictionary() {
        dictionaryDao.insert(Dictionary())
        NavScreens.navController.navigate(R.id.dictionaryFragment, Bundle().apply {

        })
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



