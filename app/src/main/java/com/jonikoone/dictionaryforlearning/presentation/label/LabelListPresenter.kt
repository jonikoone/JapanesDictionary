package com.jonikoone.dictionaryforlearning.presentation.label

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListAdapter
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment
import com.jonikoone.dictionaryforlearning.util.mvpScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

@InjectViewState
class LabelListPresenter : MvpPresenter<LabelListView>(), KoinComponent {
    val adapter = LabelListAdapter(this)
    val labelDao: LabelDao by inject()

    fun addLabel() {
        mvpScope.launch(Dispatchers.IO) {
            val labelId = labelDao.insert(Label())
            val label = labelDao.getLabel(labelId)
            loadDataToAdapter()
            withContext(Dispatchers.Main) {
                viewState.clickOnLabel(label)
            }
        }
    }

    fun getLabelList(): List<Label> {
        return labelDao.getLabels1()
    }

    fun loadDataToAdapter() {
        mvpScope.launch(Dispatchers.IO) {
            val labels = labelDao.getLabels1()
            withContext(Dispatchers.Main){
                viewState.updateList(labels)
            }
        }
    }

    fun updateList(labelList: List<Label>) {
        adapter.updateList(labelList)
    }
}