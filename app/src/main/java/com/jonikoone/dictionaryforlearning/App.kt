package com.jonikoone.dictionaryforlearning

import android.app.Application
import com.jonikoone.dictionaryforlearning.di.adaptersModule
import com.jonikoone.dictionaryforlearning.di.navigationModule
import com.jonikoone.dictionaryforlearning.di.viewModelModules
import com.jonikoone.dictionaryforlearning.entites.Word
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)

            modules(listOf(
                viewModelModules,
                navigationModule,
                adaptersModule,
                module {
                    single(named("list")) {
                        mutableListOf<Word>(
                            Word(id = 0, word = "わたし1", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし2", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし3", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし4", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし5", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし6", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし7", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし8", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし9", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし0", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし1", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし2", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし3", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし4", caseWord = "私", translate = "Я"),
                            Word(id = 0, word = "わたし5", caseWord = "私", translate = "Я")
                        )
                    }
                }
            ))
        }
    }

}