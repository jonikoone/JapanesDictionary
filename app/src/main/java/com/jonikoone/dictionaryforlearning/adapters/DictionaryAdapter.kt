package com.jonikoone.dictionaryforlearning.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentItemWordBinding
import com.jonikoone.dictionaryforlearning.entites.Word
import com.jonikoone.dictionaryforlearning.viewmodels.ItemFragmentViewModel
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class DictionaryAdapter : RecyclerView.Adapter<CommonViewHolder>(), KoinComponent {

    private val list = mutableListOf<Any>()

    fun updateList(newList: List<Any>) {
        val wordsDiffUtil = WordsDiffUtil(list, newList)
        val result = DiffUtil.calculateDiff(wordsDiffUtil)
        list.clear()
        list.addAll(newList)
        list.add(1)
        result.dispatchUpdatesTo(this)
    }

    inner class WordViewHolder(private val itemViewDataBinding: FragmentItemWordBinding) :
        CommonViewHolder(itemViewDataBinding.root) {
        override fun bind(data: Any?) {
            itemViewDataBinding.viewModel = get<ItemFragmentViewModel> { parametersOf(data) }
        }
    }

    inner class LastItemViewHolder(itemView: View) : CommonViewHolder(itemView) {
        override fun bind(data: Any?) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (viewType) {
            1 -> LastItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_last_dict_item, parent, false)
            )
            else -> WordViewHolder(
                FragmentItemWordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int =
        if (list[position] is Word)
            0
        else
            1

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        when (holder) {
            is WordViewHolder -> holder.bind(list[position])
            is LastItemViewHolder -> holder.bind(null)
            else -> {
            }
        }
    }
}

abstract class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Any?)
}

class WordsDiffUtil(private val oldList: List<Any>, private val newList: List<Any>) :
    CallbackDiffUtil<Any>(oldList, newList) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemOld = oldList[oldItemPosition]
        val itemNew = newList[newItemPosition]
        return if (itemOld is Word && itemNew is Word) {
            itemOld.id == itemNew.id
        } else false

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemOld = oldList[oldItemPosition]
        val itemNew = newList[newItemPosition]
        return if (itemOld is Word && itemNew is Word) {
            itemOld.word == itemNew.word
        } else false
    }
}

abstract class CallbackDiffUtil<in T>(
    private val oldList: Collection<T>,
    private val newList: Collection<T>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    abstract override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    abstract override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
}


/*class DictionaryAdapter
    : CommonAdapter()
    , KoinComponent {

    override fun createDiffUtilCallback(newList: List<Any>) = WordsDiffUtil(list, newList)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (viewType) {
            0 -> LastItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_last_dict_item, parent, false))
            else -> DictionaryViewHolder(FragmentItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class DictionaryViewHolder(private val viewDataBinding: FragmentItemWordBinding) : CommonViewHolder(viewDataBinding) {
        override fun <Word> bind(word: Word) {
            viewDataBinding.viewModel = get<ItemFragmentViewModel> { parametersOf(word)}
        }
    }

    inner class LastItemViewHolder(itemView: View) : CommonViewHolder(itemView) {
        override fun <Int> bind(bindData: Int) {
            itemView
        }
    }
}

class WordsDiffUtil(private val oldList: List<Word>, private val newList: List<Word>) :
    CallbackDiffUtil<Word>(oldList, newList) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].word == newList[newItemPosition].word
    }
}*/

