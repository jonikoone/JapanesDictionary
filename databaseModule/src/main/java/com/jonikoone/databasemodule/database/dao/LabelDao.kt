package com.jonikoone.databasemodule.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jonikoone.databasemodule.database.entites.Label

@Dao
interface LabelDao : BaseDao<Label>{

    @Query(value = "select * from labels")
    fun getLabels() : LiveData<List<Label>>

    //TODO test
    @Query(value = "select * from labels")
    fun getLabels1() : List<Label>

    @Query(value = "select * from labels where id_label = :idLabel")
    fun getLabel(idLabel: Long) : Label

}

