package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.*
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Label
import com.jonikoone.dictionaryforlearning.databinding.FragmentLabelsListBinding
import com.jonikoone.dictionaryforlearning.util.BaseFragment
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class LabelListFragment : BaseFragment() {

    private val labelsViewModel: LabelListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = createViewDataBinding<FragmentLabelsListBinding>(inflater, container, R.layout.fragment_labels_list)
        binding.viewModel = labelsViewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
    }


}

