package com.jonikoone.dictionaryforlearning.di

import android.content.Context
import androidx.room.Room
import com.jonikoone.dictionaryforlearning.database.AppDatabase
import org.koin.dsl.module


val databaseModule = module {
    single { (applicationContext: Context) ->
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "test")
            .build()
    }
}