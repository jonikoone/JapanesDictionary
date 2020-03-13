package com.jonikoone.dictionaryforlearning.viewmodels

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.adapters.DictionaryAdapter
import com.jonikoone.dictionaryforlearning.entites.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import java.util.*
import kotlin.coroutines.CoroutineContext

class DictionaryViewModel : ViewModel(), KoinComponent, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val adapter: DictionaryAdapter by inject()
    val homeViewModel: HomeViewModel by inject()

    private val dict: MutableList<Word> by inject(named("list"))


    init {

        adapter.updateList(dict)
        homeViewModel.onClickListener = {
            dict += Word(id = 0, word = "おんな", caseWord = "女", translate = "Женщина")
            adapter.updateList(dict)
            Log.e("Test", "click")
        }

    }


}


