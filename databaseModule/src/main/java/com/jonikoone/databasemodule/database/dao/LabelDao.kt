package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jonikoone.databasemodule.database.entites.Label

@Dao
interface LabelDao {

    @Query("select * from labels")
    fun getLabels() : LiveData<List<Label>>

    @Update
    fun updateLabel(label: Label)

    @Insert
    fun addLable(label: Label)

    @Delete
    fun deleteLabel(label: Label)

}