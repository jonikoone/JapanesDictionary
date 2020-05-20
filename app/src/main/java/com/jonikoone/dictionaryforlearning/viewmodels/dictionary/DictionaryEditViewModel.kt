package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.util.SuspendWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class DictionaryEditViewModel(dictionaryDao: DictionaryDao, dictionary: Dictionary) : ViewModel(),
        SuspendWork<Dictionary>, KoinComponent {

    val router by inject<Router> ()

    val titleDictionary = MutableLiveData<String>()
    val labelColor = MutableLiveData<Int>()

    val backAction = View.OnClickListener {
        //router.exit()
    }

    init {

    }

    override var job: Job? = null
    override val delaySuspend: Long = 200
    override val work: (Dictionary) -> Unit = {
        dictionaryDao.update(it)
    }


}

class MyAdapter : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

}