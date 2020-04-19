package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.DictionaryWithLabel
import com.jonikoone.databasemodule.database.entites.DictionaryWithWords
import com.jonikoone.databasemodule.database.entites.Word

@Dao
interface DictionaryDao : BaseDao<Dictionary> {

    @Query(value = "Select * from dictionaries")
    fun getDictionaries() : LiveData<List<Dictionary>>

    @Transaction
    @Query(value = "select * from dictionaries")
    fun getDictionaryWithWirds() : LiveData<List<DictionaryWithWords>>

    @Transaction
    @Query(value = "select * from dictionaries")
    fun getDictionaryWithLabel() : LiveData<List<DictionaryWithLabel>>

    @Transaction
    @Query(value = "select * from dictionaries where id_dictionary = :dictionaryId")
    fun getWordFromDictionary(dictionaryId: Long) : LiveData<DictionaryWithWords>

    @Query(value = "select * from dictionaries where id_dictionary = :dictionaryId")
    fun getDictionaryItem(dictionaryId: Long) : LiveData<Dictionary>

    @Transaction
    @Query(value = "select * from dictionaries where id_dictionary = :dictionaryId")
    fun getDictionaryWithLabelItem(dictionaryId: Long) : LiveData<DictionaryWithLabel>


}