package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.databasemodule.database.entites.WordStatistic
import com.jonikoone.databasemodule.database.entites.WordWithStatistic

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

    // statistic

    @Insert
    fun insert(wordStatistic: WordStatistic): Long

    @Update
    fun update(wordStatistic: WordStatistic)

    @Delete
    fun delete(wordStatistic: WordStatistic)

    @Query(value = "select * from words_statistic")
    fun getStatistics(): LiveData<List<WordStatistic>>

    @Query("select * from words_statistic where id_word_statistic = :id")
    fun getStatisticById(id: Long) : LiveData<WordStatistic>

    @Query("select * from words_statistic where wordOwnerId = :id")
    fun getStatisticByWordId(id: Long) : LiveData<WordStatistic>

    // with statistic
    @Transaction
    @Query("select * from words")
    fun getWordsWithStatistic(): LiveData<List<WordWithStatistic>>

    @Transaction
    @Query("select * from words where id_word = :id")
    fun getWordWithStatisticById(id: Long): LiveData<WordWithStatistic>

    @Transaction
    fun createWordWithStatistic(word: Word, statistic: WordStatistic = WordStatistic()): Long {
        val id = insert(word)
        val newStatistic = statistic.copy(wordOwnerId = id)
        insert(newStatistic)
        return id
    }

}
