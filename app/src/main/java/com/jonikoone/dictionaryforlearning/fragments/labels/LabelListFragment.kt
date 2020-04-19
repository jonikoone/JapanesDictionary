package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentLabelsListBinding
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber

class LabelListFragment : Fragment() {

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
        val binding = DataBindingUtil.inflate<FragmentLabelsListBinding>(inflater, R.layout.fragment_labels_list, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = labelsViewModel
        return binding.root
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

