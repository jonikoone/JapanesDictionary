package com.jonikoone.databasemodule.database.entites

import androidx.room.*

@Entity(tableName = "dictionaries")
data class Dictionary(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_dictionary")
    val id: Long,
    val title: String,
    @ColumnInfo(name = "id_dictionary_label")
    val labelId: Int? = null
)

data class DictionaryWithWords(
    @Embedded val dictionary: Dictionary,
    @Relation(
        parentColumn = "id_dictionary",
        entityColumn = "id_to_dictionary"
    )
    val words: List<Word>
)

data class DictionaryWithLabel(
    @Embedded val dictionary: Dictionary,
    @Relation(
        parentColumn = "id_dictionary",
        entityColumn = "id_to_dictionary"
    )
    val label: Label
)