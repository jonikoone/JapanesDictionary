package com.jonikoone.databasemodule.database.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "labels")
data class Label(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_label")
    val id: Long,
    @ColumnInfo(name = "label_title", defaultValue = "")
    val title: String = "",
    @ColumnInfo(name = "label_difficulty", defaultValue = "0")
    val difficulty: Int = 0,
    @ColumnInfo(name = "label_color", defaultValue = "0")
    val color: Int
) : Serializable