package com.jonikoone.databasemodule.database.entites

data class Dictionary(
    val id: Long,
    val title: String,
    val label: Int = 0
)