package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.DictionaryWithLabel
import com.jonikoone.databasemodule.database.entites.Label

@Dao
interface DictionaryWithLabelDao {

    @Query("select * from dictionaries")
    fun getDictionariesAsList(): List<Dictionary>

    @Query("select * from dictionaries")
    fun getDictionariesAsLive(): LiveData<List<Dictionary>>

    @Query("select * from labels")
    fun getLabelsAsList(): List<Label>

    @Query("select * from labels")
    fun getLabelsAsLive(): LiveData<List<Label>>

    @Query("select * from dictionaries where id_dictionary = :dictionaryId")
    fun getDictionary(dictionaryId: Long): Dictionary

    @Query("Select * from labels where id_label = :labelId")
    fun getLabel(labelId: Long): Label

    /*@Transaction
    fun getDictionaryWithLabel(dictionaryId: Long) : DictionaryWithLabel {
        val dict = getDictionary(dictionaryId)
        return DictionaryWithLabel(
            dictionary = dict,
            label = if (dict.labelId != null) {
                getLabel(dict.labelId!!)
            } else null
        )
    }

    @Transaction
    fun getDictionaryWithLabelsAsList() : List<DictionaryWithLabel> {
        val dicts = getDictionariesAsList()
        val labels = getLabelsAsList()
        return dicts.map {d ->
            DictionaryWithLabel(
                dictionary = d,
                label = d.labelId?.let {id ->
                    labels.first { it.id == id }
                }
            )
        }
    }*/

    /*@Transaction
    fun getDictionaryWithLabelsAsLive() : LiveData<List<DictionaryWithLabel>> {
        return MutableLiveData<List<DictionaryWithLabel>>().apply {
            value = getDictionaryWithLabelsAsList()
        }
    }*/


}