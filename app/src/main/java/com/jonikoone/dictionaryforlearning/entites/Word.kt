package com.jonikoone.dictionaryforlearning.entites

data class Word(
    val id: Long,
    val word: String,
    val caseWord: String,
    val translate: String
)

data class StatisticTest(
    val id: Long,
    val countTry: Int,
    val failedTry: Int
)
