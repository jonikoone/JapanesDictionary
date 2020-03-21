package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.BaseFragment
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import org.koin.android.viewmodel.ext.android.viewModel

class DictionaresFragment : BaseFragment() {

    private val viewModelDictionares: DictionaresViewModel by viewModel()

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDictionaresBinding = createViewDataBinding(inflater, container, R.layout.fragment_dictionares)
        binding.viewModel = viewModelDictionares

        return binding.root
    }*/

}

class DictionaresViewModel : ViewModel() {
    val adapter = DictionaresAdapter()
}

class DictionaresAdapter : BaseAdapter<Dictionary>() {

    override fun createDiffCallback(
        oldList: List<Dictionary>,
        newList: List<Dictionary>
    ): DiffCallback<Dictionary> =
        object : DiffCallback<Dictionary>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].title == newList[newItemPosition].title
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Dictionary> {
        TODO("Not yet implemented")
    }

}
