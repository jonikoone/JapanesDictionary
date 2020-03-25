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
    @ColumnInfo(name = "word", defaultValue = "")
    val word: String = "",
    @ColumnInfo(name = "word_case", defaultValue = "")
    val caseWord: String = "",
    @ColumnInfo(name = "word_translate", defaultValue = "")
    val translate: String = ""
) : Serializable

/*data class CommonStatisticWord(
    @ColumnInfo(name = "word_statistic_count_testing", defaultValue = "0")
    val countTesting: Int = 0,
    @ColumnInfo(name = "word_statistic_count_failed", defaultValue = "0")
    val failedTry: Int = 0
)*/


/*@Entity(tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_test")
    val id: Long,
    @Embedded
    val statistic: CommontStatisticTest = CommontStatisticTest()
)

data class CommontStatisticTest(
    val countTesting: Int = 0,
    val failedTry: Int = 0
)

@Entity(tableName = "test_word_cross_ref", primaryKeys = ["id_word", "id_test"])
data class TestWordCrossRef(
    @ColumnInfo(name = "id_word")
    val idWord: Long,
    @ColumnInfo(name = "id_test")
    val idTest: Long
)*/


/*@Entity(tableName = "label_word_cross_ref", primaryKeys = ["id_label", "id_word"])
data class LabelWordCrossRef(
    @ColumnInfo(name = "id_label")
    val idLabel: Long,
    @ColumnInfo(name = "id_word")
    val idWord: Long
)*/

/*@Entity(tableName = "label_test_cross_ref", primaryKeys = ["id_label", "id_test"])
data class LabelTestCrossRef(
    @ColumnInfo(name = "id_label")
    val idLabel: Long,
    @ColumnInfo(name = "id_test")
    val idTest: Long
)*/
//////////////////statistics

/*
data class WordStatistics(
    val id: Long,
    val wordId: Long,
    val date: Long,
    val
)*/

/*data class TestStatistic(
    val id: Long,
    val template: Test,
    val countTesting: Int,
    val countFailed: Int,
    val wordsFailed: List<Word>
)*/