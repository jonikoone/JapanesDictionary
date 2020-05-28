package com.jonikoone.dictionaryforlearning.presentation.label

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListAdapter
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.util.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class LabelListPresenter : BasePresenter<LabelListView>(), KoinComponent {
    val labelDao: LabelDao by inject()
    val router: Router by inject()

    lateinit var labeles: LiveData<List<Label>>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        labeles = labelDao.getLabels().apply {
            Timber.d("live data update: labels = $this")
            observeForever { data ->
                viewState.updateList(data)
            }
        }
    }

    fun addLabel() {
        presenterScope.launch(Dispatchers.IO) {
            val label = labelDao.insert(Label())
            withContext(Dispatchers.Main) {
                router.navigateTo(Screens.LabelScreen(label))
            }
        }
    }
}