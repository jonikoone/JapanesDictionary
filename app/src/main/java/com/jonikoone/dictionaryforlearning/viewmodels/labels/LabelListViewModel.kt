package com.jonikoone.dictionaryforlearning.viewmodels.labels

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Label
import com.jonikoone.dictionaryforlearning.databinding.ItemLabelBinding
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import timber.log.Timber

class LabelListViewModel : ViewModel(), KoinComponent {

    val adapter = LabelListAdapter()

    val list: List<Label> by inject(named("labels"))

    init {
        adapter.updateList(list)
    }

    fun addLabel() {
        adapter.addItems(Label(0L, "New label", 128.toByte(), Color.GREEN))
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
        object : DiffCallback<Label>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                try {
                    list[oldItemPosition].id == list[newItemPosition].id
                } catch (e: IndexOutOfBoundsException) {
                    false
                }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                try {
                    list[oldItemPosition].title == list[newItemPosition].title
                } catch (e: IndexOutOfBoundsException) {
                    false
                }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Label> =
        LabelViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_label,
                parent,
                false
            )
        )

    inner class LabelViewHolder(private val binding: ItemLabelBinding) :
        BaseViewHolder<Label>(binding), KoinComponent {
        override fun bind(data: Label) {
            binding.itemViewModel = get { parametersOf(data) }
        }

    }

}
