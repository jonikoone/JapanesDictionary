package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.presentation.label.LabelListPresenter
import com.jonikoone.dictionaryforlearning.presentation.label.LabelListView
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber

class LabelListFragment : MvpAppCompatFragment(), LabelListView {

    @InjectPresenter
    lateinit var presenter: LabelListPresenter

//    private val labelsViewModel: LabelListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_labels_list, container, false)
    }

    override fun clickOnAddLabel() {
        presenter.addLabel()
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Timber.d("labelfragment: create menu")
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.labels_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.addLabel -> {
            Timber.d("labelfragment: add label")
            labelsViewModel.addLabel()
            true
        }
        else -> {
            Timber.d("labelfragment: else")
            super.onOptionsItemSelected(item)
        }
    }*/


}

