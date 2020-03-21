package com.jonikoone.dictionaryforlearning.viewmodels.dictionary

/*import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.databinding.FragmentDictionaresBinding
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaresViewModel
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import com.jonikoone.dictionaryforlearning.viewmodels.words.WordsListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext


class DictionaryViewModel : WordsListViewModel(null) {

}*/

/*class DictionaryViewModel(private val dictionary: Dictionary) : ViewModel(), KoinComponent, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val adapter: DictionaryAdapter = DictionaryAdapter()

    val titleDictionary: String = dictionary.title

    private val dict: MutableList<Word> by inject(named("list"))


    init {

        adapter.updateList(dict)


    }


}

class DictionaryAdapter(oldList: List<Dictionary>, newList: List<Dictionary>) : BaseAdapter<Dictionary>() {
    override fun createDiffCallback(
        oldList: List<Dictionary>,
        newList: List<Dictionary>
    ): DiffCallback<Dictionary> =
        object : DiffCallback<Dictionary>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].id == list[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].title == list[newItemPosition].title

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Dictionary> =
        DictionaryViewHolder(DataBindingUtil.inflate<FragmentDictionaresBinding>(LayoutInflater.from(parent.context), R.layout.fragment_dictionares, parent, false))

    inner class DictionaryViewHolder(private val binding: FragmentDictionaresBinding) : BaseViewHolder<Dictionary>(itemView.root) {
        override fun bind(data: Dictionary) {
            binding.viewModel =
        }

    }
}*/


