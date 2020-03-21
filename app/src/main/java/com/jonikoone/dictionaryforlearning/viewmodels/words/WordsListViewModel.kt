package com.jonikoone.dictionaryforlearning.viewmodels.words

/*import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Word
import com.jonikoone.dictionaryforlearning.databinding.ItemWordBinding
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

open class WordsListViewModel(val dictionaryId: Long? = null) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val titleDictionary: MutableLiveData<String> = MutableLiveData("")
    val adapter = WordsAdapter()

    init {
        launch(Dispatchers.IO) {

        }
    }

}

class WordsAdapter : BaseAdapter<Word>() {
    override fun createDiffCallback(oldList: List<Word>, newList: List<Word>): DiffCallback<Word> =
        object : DiffCallback<Word>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].id == list[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].word == list[newItemPosition].word

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Word> =
        WordViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_word,
                parent,
                false
            )
        )

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        BaseViewHolder<Word>(binding.root), KoinComponent {
        override fun bind(data: Word) {
            binding.viewModel = get { parametersOf(data) }
        }

    }

}*/
