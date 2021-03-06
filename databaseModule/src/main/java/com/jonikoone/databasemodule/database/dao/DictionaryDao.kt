package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.DictionaryWithLabel
import com.jonikoone.databasemodule.database.entites.DictionaryWithWords
import com.jonikoone.databasemodule.database.entites.Label

@Dao
interface DictionaryDao : BaseDao<Dictionary> {

    @Query(value = "select * from dictionaries")
    fun getDictionariesLive(): LiveData<List<Dictionary>>

    @Query(value = "select * from dictionaries")
    fun getDictionariesList(): List<Dictionary>

    @Query(value = "select * from dictionaries where id_dictionary = :dictionaryId")
    fun getDictionaryItem(dictionaryId: Long): Dictionary

    // with label
    @Transaction
    @Query(value = "select * from dictionaries")
    fun getDictionariesWithLabelAsLive(): LiveData<List<DictionaryWithLabel>>

    @Transaction
    @Query(value = "select * from dictionaries where id_dictionary = :dictionaryId")
    fun getDictionaryWithLabelItem(dictionaryId: Long): DictionaryWithLabel


    // with words
    @Transaction
    @Query(value = "select * from dictionaries")
    fun getDictionariesWithWords(): LiveData<List<DictionaryWithWords>>

    @Transaction
    @Query(value = "select * from dictionaries where id_dictionary = :dictionaryId")
    fun getDictionaryWithWords(dictionaryId: Long): LiveData<DictionaryWithWords>


    @Transaction
    fun insertAndGet(dictionary: Dictionary): Dictionary {
        return getDictionaryItem(insert(dictionary))
    }



}