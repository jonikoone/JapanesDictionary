package com.jonikoone.dictionaryforlearning.database.dao

import androidx.room.*
import com.jonikoone.dictionaryforlearning.database.entites.Label
import kotlinx.coroutines.selects.select

@Dao
interface LabelDao {

    @Query("select * from labels")
    fun getLabels() : List<Label>

    @Update
    fun updateLabel(label: Label)

    @Insert
    fun addLable(label: Label)

}