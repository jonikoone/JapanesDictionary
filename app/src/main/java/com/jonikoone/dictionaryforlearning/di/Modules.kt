package com.jonikoone.dictionaryforlearning.di

import androidx.room.Room
import com.jonikoone.databasemodule.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "test")
            .build()
    }
}