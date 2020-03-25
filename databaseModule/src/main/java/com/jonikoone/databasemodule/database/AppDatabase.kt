package com.jonikoone.databasemodule.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.*

@Database(
    entities = [
        Word::class,
        Label::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao
//    abstract fun getTestDao(): TestDao
    abstract fun getLabelDao(): LabelDao

}