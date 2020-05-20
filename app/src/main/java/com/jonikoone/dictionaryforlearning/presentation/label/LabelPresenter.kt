package com.jonikoone.dictionaryforlearning.presentation.label

import com.arellomobile.mvp.MvpPresenter
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.util.mvpScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class LabelPresenter : MvpPresenter<LabelView>(), KoinComponent {

    val labelDao: LabelDao by inject()

    suspend fun reloadLabel(id: Long): Label {
        return withContext(mvpScope.coroutineContext + Dispatchers.IO) {
            labelDao.getLabel(id)
        }
    }

    fun updateLabel(label: Label) {
        mvpScope.launch(Dispatchers.IO) {
            if (label.title.isNotEmpty())
                labelDao.update(label)
            else
                labelDao.delete(label)
        }
    }


}