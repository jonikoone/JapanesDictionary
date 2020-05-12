package com.jonikoone.dictionaryforlearning.presentation.label

import com.arellomobile.mvp.MvpPresenter
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class LabelListPresenter : MvpPresenter<LabelListView>(), KoinComponent {
    val labelDao: LabelDao by inject()

    fun addLabel() {
        CoroutineScope(Dispatchers.IO).launch {
            labelDao.insert(Label())
        }
    }
}