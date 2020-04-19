package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jonikoone.databasemodule.database.entites.Word

@Dao
interface WordDao : BaseDao<Word> {

    @Query("Select * From words")
    fun getWords(): LiveData<List<Word>>

    @Query("Select * From words where id_word = :id")
    fun getWord(id: Long): Word

    @Query("Select * From words where word_translate = :translateWord")
    fun getWordByTranslateWord(translateWord: String): Word

    @Query("Select * From words where word = :word")
    fun getWordByWord(word: String): Word

    @Query("Select * From words where word_case = :caseWord")
    fun getWordByCase(caseWord: String): Word


    /*@Query("select * from words where wor")
    fun getWordsFromDictionary(dictionaryId: Long)*/


}
