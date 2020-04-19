package com.jonikoone.databasemodule.database.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_word")
    val id: Long = 0,
    @ColumnInfo(name = "id_to_dictionary")
    val dictionaryId: Long? = null,
    @ColumnInfo(name = "word", defaultValue = "")
    val word: String = "",
    @ColumnInfo(name = "word_case", defaultValue = "")
    val caseWord: String = "",
    @ColumnInfo(name = "word_translate", defaultValue = "")
    val translate: String = ""
) : Serializable

