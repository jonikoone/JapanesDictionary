package com.jonikoone.dictionaryforlearning.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonikoone.dictionaryforlearning.database.dao.LabelDao
import com.jonikoone.dictionaryforlearning.database.entites.*

@Database(
    entities = [
        Label::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

//    abstract fun getWordDao(): WordDao
//    abstract fun getTestDao(): TestDao
    abstract fun getLabelDao(): LabelDao

}