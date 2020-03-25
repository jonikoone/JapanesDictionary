package com.jonikoone.dictionaryforlearning

//import com.jonikoone.dictionaryforlearning.di.navigationModule
import android.app.Application
import android.graphics.Color
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.di.databaseModule
import com.jonikoone.dictionaryforlearning.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.d("result %d", Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModules,
//                navigationModule,
                    databaseModule,
                    module {
                        single(named("labels")) {
                            listOf(
                                Label(
                                    0,
                                    "label ",
                                    0,
                                    Color.BLUE
                                ),
                                Label(
                                    0,
                                    "label ",
                                    0,
                                    Color.BLUE
                                ),
                                Label(
                                    0,
                                    "label ",
                                    0,
                                    Color.BLUE
                                ),
                                Label(
                                    0,
                                    "label ",
                                    0,
                                    Color.BLUE
                                )
                            )
                        }
                    }
                    /*module {
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
                    }*/
                )
            )
        }
    }

}