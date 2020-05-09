package com.jonikoone.dictionaryforlearning.viewmodels.words

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.ItemWordBinding
import com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment.Companion.WORD_ARG
import com.jonikoone.dictionaryforlearning.generated.callback.OnClickListener
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

open class WordsListViewModel(private val database: AppDatabase, val dictionaryId: Long? = null) :
        ViewModel(), KoinComponent {

    val isFabRotate = MutableLiveData(false)

    val titleDictionary: MutableLiveData<String> = MutableLiveData("All Words")
    val adapter = WordsAdapter()

    val createSelectionTracker: (RecyclerView) -> Unit = {
        val tracker = SelectionTracker.Builder<Long>(
                "my selection", it,
                StableIdKeyProvider(it),
                WordItemDetailLookup(it),
                StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
        ).build()

        tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()

                        /*if (tracker.selection.isEmpty) {
                            Toast.makeText(it.context, "selection is empty", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(it.context, "selection is not empty", Toast.LENGTH_SHORT).show()
                        }*/
                    }
                })

        adapter.tracker = tracker
    }

    val backAction = View.OnClickListener {
        NavScreens.navController.popBackStack()
    }

    val showFABs = View.OnLongClickListener {
        it as FloatingActionButton
        it.isExpanded = !it.isExpanded
        true
    }

    val addWord = View.OnClickListener {

        /*it as FloatingActionButton
        it.isExpanded = !it.isExpanded
        it.isActivated = !it.isActivated

        isFabRotate.value = it.isExpanded*/

        it as FloatingActionButton
        if (it.isExpanded) {

        } else {
            viewModelScope.launch {
                var newWord: Word? = null
                withContext(Dispatchers.IO) {
                    var newWordId = database.getWordDao().insert(Word())
                    newWord = database.getWordDao().getWord(newWordId)
                }
                newWord?.let { word ->
                    NavScreens.navController.navigate(R.id.wordFragment, bundleOf(WORD_ARG to word))
                }
            }
        }
    }

    init {
        if (dictionaryId != null) {
            database.getDictionaryDao().getDictionaryWithWords(dictionaryId).observeForever {
                titleDictionary.value = it.dictionary.title
                adapter.updateList(it.words)
            }
        } else {
            database.getWordDao().getWords().observeForever {
                adapter.updateList(it)
            }
        }
    }


    fun deleteSelectedWords() {
        adapter.tracker?.selection?.forEach {
            Timber.d("selection items: $it")
        }
    }

    inner class WordsAdapter : BaseAdapter<Word>() {

        var tracker: SelectionTracker<Long>? = null

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long = position.toLong()

        override fun createDiffCallback(
                oldList: List<Word>,
                newList: List<Word>
        ): DiffCallback<Word> =
                object : DiffCallback<Word>(oldList, newList) {
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                            oldList[oldItemPosition].id == newList[newItemPosition].id

                    override fun areContentsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                    ): Boolean =
                            oldList[oldItemPosition].word == newList[newItemPosition].word
                                    || oldList[oldItemPosition].translate == newList[newItemPosition].translate
                                    || oldList[oldItemPosition].caseWord == newList[newItemPosition].caseWord


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

        override fun onBindViewHolder(holder: BaseViewHolder<Word>, position: Int) {
            val word = list[position]
            tracker?.let {
                holder.bind(word, it.isSelected(position.toLong()))
            }

        }

        inner class WordViewHolder(private val binding: ItemWordBinding) :
                BaseViewHolder<Word>(binding), KoinComponent {

            override fun bind(data: Word, isActivate: Boolean) {
                binding.viewModel = get { parametersOf(data) }
                binding.viewModel?.checkable?.value = isActivate
                binding.root.isActivated = isActivate
            }

            fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
        }

    }


    class WordItemDetailLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null)
                return (recyclerView.getChildViewHolder(view) as WordsAdapter.WordViewHolder).getItemDetails()
            return null
        }

    }

}

