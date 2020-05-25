package com.jonikoone.databasemodule.database.entites

import androidx.room.*
import com.jonikoone.databasemodule.database.OpenForTesting

@Entity(tableName = "dictionaries")
@OpenForTesting
data class Dictionary(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_dictionary")
    val id: Long = 0,
    val title: String = "",
    @ColumnInfo(name = "id_dictionary_label")
    val labelId: Long? = null
)

@OpenForTesting
data class DictionaryWithWords(
        @Embedded val dictionary: Dictionary,
        @Relation(
        parentColumn = "id_dictionary",
        entityColumn = "id_to_dictionary"
    )
    val words: MutableList<Word>
)

@OpenForTesting
data class DictionaryWithLabel(
        @Embedded val dictionary: Dictionary,
        @Relation(
        parentColumn = "id_dictionary",
        entityColumn = "id_label"
    )
    val label: Label?
)