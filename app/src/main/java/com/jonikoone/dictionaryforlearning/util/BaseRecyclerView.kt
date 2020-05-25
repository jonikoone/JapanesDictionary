package com.jonikoone.dictionaryforlearning.util

import android.view.View
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class BaseAdapter<T> :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    protected val list: MutableList<T> = mutableListOf()

    fun deleteItems(vararg  items: T) {
        updateList(list - items)
    }

    fun addItems(vararg newItems: T) {
        updateList(list + newItems)
    }

    fun updateList(newList: List<T>) {
        Timber.d("update list:\n$newList")
        val result = DiffUtil.calculateDiff(createDiffCallback(list, newList))
        list.clear()
        list.addAll(newList)

        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(data: T){}
        open fun bind(data: T, isActivate: Boolean = false){}
    }

    abstract fun createDiffCallback(oldList: List<T>, newList: List<T>): DiffCallback<T>


}

abstract class DiffCallback<T>(private val oldList: List<T>, private val newList: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.count()
    override fun getNewListSize(): Int = newList.count()
}

