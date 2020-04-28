package com.jonikoone.databasemodule.database.entites

import androidx.room.*
import com.jonikoone.databasemodule.database.OpenForTesting
import org.jetbrains.annotations.PropertyKey
import java.io.Serializable

@Entity(tableName = "words")
@OpenForTesting
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_word")
    val id: Long = 0,
    @ColumnInfo(name = "id_to_dictionary")
    val dictionaryId: Long? = null,
    @ColumnInfo(name = "word")
    val word: String = "",
    @ColumnInfo(name = "word_case")
    val caseWord: String = "",
    @ColumnInfo(name = "word_translate")
    val translate: String = ""
) : Serializable

@Entity(tableName = "words_statistic")
@OpenForTesting
data class WordStatistic(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_word_statistic")
        val id: Long = 0,
        @ColumnInfo(name = "count_repeat")
        val countRepeat: Int = 0,
        @ColumnInfo(name = "count_fail")
        val countFail: Int = 0,
        val wordOwnerId: Long = 0
) : Serializable

@OpenForTesting
data class WordWithStatistic(
        @Embedded val word: Word,
        @Relation(
                parentColumn = "id_word",
                entityColumn = "wordOwnerId"
        )
        val statistic: WordStatistic?


)