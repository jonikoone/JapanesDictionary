package com.jonikoone.databasemodule.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(entitiy: T): Long

    @Update
    fun update(entitiy: T)

    @Delete
    fun delete(entitiy: T)
}