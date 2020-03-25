package com.jonikoone.dictionaryforlearning.viewmodels.labels

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.ItemLabelBinding
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

class LabelListViewModel : ViewModel(), KoinComponent {

    private val database: AppDatabase by inject()

    val adapter = LabelListAdapter()


    init {
        database.getLabelDao().getLabels().observeForever{
            adapter.updateList(it)
        }
    }


    fun addLabel() {
        adapter.addItems(
            Label(
                0L,
                "New label",
                128,
                Color.GREEN
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            database.getLabelDao().addLable(
                Label(
                    0L,
                    "New label",
                    128,
                    Color.YELLOW
                )
            )
        }
    }

    fun click() {
        Timber.d("labelfragment: click")
        addLabel()
    }
}

class LabelListAdapter : BaseAdapter<Label>() {
    override fun createDiffCallback(
        oldList: List<Label>,
        newList: List<Label>
    ): DiffCallback<Label> =
        object :
            DiffCallback<Label>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].id == newList[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].title == newList[newItemPosition].title ||
                        oldList[oldItemPosition].difficulty == newList[newItemPosition].difficulty ||
                        oldList[oldItemPosition].color == newList[newItemPosition].color


        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Label> =
        LabelViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_label,
                parent,
                false
            )
        )

    inner class LabelViewHolder(private val binding: ItemLabelBinding) :
        BaseViewHolder<Label>(binding),
        KoinComponent {
        override fun bind(data: Label) {
            binding.itemViewModel = get { parametersOf(data) }
        }

    }

}
