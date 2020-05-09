package com.jonikoone.databasemodule

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.databasemodule.database.entites.WordStatistic
import com.jraska.livedata.test
import kotlinx.coroutines.awaitAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var labelDao: LabelDao
    private lateinit var dictionaryDao: DictionaryDao
    private lateinit var wordDao: WordDao

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java).build()
        labelDao = db.getLabelDao()
        dictionaryDao = db.getDictionaryDao()
        wordDao = db.getWordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeLabel() {
        val label = mock(Label::class.java)
        `when`(label.title).thenReturn("test label")
        val labelId = labelDao.insert(label)
        labelDao.getLabel(labelId).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.id != 0L }
                .assertValue { it.title == label.title }
    }

    @Test
    @Throws(Exception::class)
    fun writeDictionaryWithLabel() {
        val newLabel = mock(Label::class.java)
        `when`(newLabel.title).thenReturn("label for dict")
        val idLabel = labelDao.insert(newLabel)

        val newDict = mock(Dictionary::class.java)
        `when`(newDict.title).thenReturn("test dict")
        `when`(newDict.labelId).thenReturn(idLabel)

        val idDict = dictionaryDao.insert(newDict)

        dictionaryDao.getDictionariesWithLabel().test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.count() == 1 }

        dictionaryDao.getDictionaryWithLabelItem(idDict).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.label?.title == newLabel.title }
                .assertValue { it.dictionary.title == newDict.title }
    }

    @Test
    @Throws(Exception::class)
    fun writeDictionary() {
        val newDict = mock(Dictionary::class.java)
        `when`(newDict.title).thenReturn("test dict")

        val idDict = dictionaryDao.insert(newDict)

        dictionaryDao.getDictionariesWithLabel().test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.count() == 1 }

        dictionaryDao.getDictionaryWithLabelItem(idDict).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.label == null }
    }

    @Test
    @Throws(Exception::class)
    fun writeWordsInDictionary() {
        val newDict = mock(Dictionary::class.java)
        `when`(newDict.title).thenReturn("test dict")
        val idDict = dictionaryDao.insert(newDict)

        dictionaryDao.getDictionaryWithWords(idDict).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.dictionary.title == newDict.title }
                .assertValue { it.words.isEmpty() }

        val newWord = mock(Word::class.java)
        `when`(newWord.word).thenReturn("str1")
        `when`(newWord.translate).thenReturn("str2")
        `when`(newWord.caseWord).thenReturn("case with word str1 IRL")
        `when`(newWord.dictionaryId).thenReturn(idDict)

        wordDao.insert(newWord)

        dictionaryDao.getDictionaryWithWords(idDict).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.dictionary.title == newDict.title }
                .assertValue { it.words.isNotEmpty() }
                .assertValue { it.words.first().word == newWord.word }
    }


    @Test
    @Throws(Exception::class)
    fun updateStatisticWord() {
        val newDict = mock(Dictionary::class.java)
        `when`(newDict.title).thenReturn("test dict")
        val idDict = dictionaryDao.insert(newDict)

        val newWord = mock(Word::class.java)
        `when`(newWord.word).thenReturn("str1")
        `when`(newWord.translate).thenReturn("str2")
        `when`(newWord.caseWord).thenReturn("case with word str1 IRL")
        `when`(newWord.dictionaryId).thenReturn(idDict)

        val idWord = wordDao.createWordWithStatistic(newWord)

        wordDao.getWordWithStatisticById(idWord).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.statistic != null }
                .assertValue { it.statistic?.countFail == 0 }

        val statistic = wordDao.getStatisticByWordId(idWord).test()
                .awaitValue()
                .assertHasValue()
                .value()

        wordDao.update(statistic.copy(countRepeat = 10, countFail = 5))

        wordDao.getStatisticByWordId(idWord).test()
                .awaitValue()
                .assertHasValue()
                .assertValue { it.countFail == 5 }

    }

}
