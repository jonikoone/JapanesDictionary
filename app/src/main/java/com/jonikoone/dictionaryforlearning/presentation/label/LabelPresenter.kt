package com.jonikoone.dictionaryforlearning.presentation.label

import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.util.BasePresenter
import com.jonikoone.dictionaryforlearning.util.TAG_BASE_PRESENTER
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.presenterScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

@InjectViewState
class LabelPresenter(val labelDao: LabelDao, val labelId: Long)
    : BasePresenter<LabelView>() {

    private lateinit var label: Label

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadLabel(labelId)
    }

    override fun onDestroy() {
        updateOrDeleteLabel(label)
        super.onDestroy()
    }

    fun loadLabel(labelId: Long) {
        presenterScope.launch(Dispatchers.IO) {
            label = labelDao.getLabel(labelId)
            withContext(Dispatchers.Main) {
                viewState.updateLabelColor(label.color)
                viewState.updateLabelDifficulty(label.difficulty)
                viewState.updateLabelDifficultyText(label.difficulty)
                viewState.updateLabelTitle(label.title)
            }

        }
    }

    private fun updateOrDeleteLabel(label: Label) {
        CoroutineScope(Job() + Dispatchers.Main).launch() {
            withContext(Dispatchers.IO) {
                Timber.d("Delete if empty")
                if (label.title.isEmpty())
                    labelDao.delete(label)
                else
                    labelDao.update(label)
            }
        }
    }

    fun updateColor(color: Int) {
        presenterScope.launch(Dispatchers.IO) {
            label = labelDao.updateAndGet(label.copy(color = color))
            withContext(Dispatchers.Main) {
                viewState.updateLabelColor(label.color)
            }
        }
    }

    fun updateTitle(title: String) {
        label = label.copy(title = title)
    }

    fun updateDifficulty(difficulty: Int) {
        label = label.copy(difficulty = difficulty)
        viewState.updateLabelDifficultyText(difficulty)
    }

}