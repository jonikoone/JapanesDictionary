package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import org.koin.android.viewmodel.ext.android.viewModel

class DictionaresFragment : Fragment() {

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

class DictionaresAdapter : BaseAdapter<com.jonikoone.databasemodule.database.entites.Dictionary>() {

    override fun createDiffCallback(
        oldList: List<com.jonikoone.databasemodule.database.entites.Dictionary>,
        newList: List<com.jonikoone.databasemodule.database.entites.Dictionary>
    ): DiffCallback<com.jonikoone.databasemodule.database.entites.Dictionary> =
        object : DiffCallback<com.jonikoone.databasemodule.database.entites.Dictionary>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].title == newList[newItemPosition].title
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<com.jonikoone.databasemodule.database.entites.Dictionary> {
        TODO("Not yet implemented")
    }

}
