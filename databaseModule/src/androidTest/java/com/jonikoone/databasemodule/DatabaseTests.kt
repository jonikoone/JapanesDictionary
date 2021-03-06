package com.jonikoone.databasemodule

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.dao.DictionaryWithLabelDao
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.dao.WordDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jraska.livedata.test
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var labelDao: LabelDao
    private lateinit var dictionaryDao: DictionaryDao
    private lateinit var dictionaryWithLabelDao: DictionaryWithLabelDao
    private lateinit var wordDao: WordDao

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        labelDao = db.getLabelDao()
        dictionaryDao = db.getDictionaryDao()
        dictionaryWithLabelDao = db.getDictionaryWithLabelDao()
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
        val label1 = labelDao.getLabel(labelId)
        assert(label1.id != 0L)
        assert(label1.title == label.title)
    }

    @Test
    @Throws(Exception::class)
    fun writeDictionaryWithLabel() {
        fun createFakeLabel(count: Int = 1): Long{
            for (i in 0..count) {
                val l = mock(Label::class.java)
                `when`(l.title).thenReturn("fake $i label")
                labelDao.insert(l)
            }
            return Random(1).nextLong(count.toLong())
        }

        val labelId = createFakeLabel(10)

        val newDict1 = mock(Dictionary::class.java)
        `when`(newDict1.title).thenReturn("test dict 1")
        `when`(newDict1.labelId).thenReturn(labelId)

        val newDict2 = mock(Dictionary::class.java)
        `when`(newDict2.title).thenReturn("test dict 2")
        `when`(newDict2.labelId).thenReturn(labelId)

        val idDict1 = dictionaryDao.insert(newDict1)
        val idDict2 = dictionaryDao.insert(newDict2)

        val dicts = dictionaryDao.getDictionariesWithLabelAsLive().test()
            .awaitValue()
            .assertHasValue()
            .assertValue {list ->
                list.forEach {
                    if (it.label == null) return@assertValue false
                }
                true
            }

    }

    @Test
    @Throws(Exception::class)
    fun writeDictionary() {
        val newDict = mock(Dictionary::class.java)
        `when`(newDict.title).thenReturn("test dict")

        val idDict = dictionaryDao.insert(newDict)

        assert(dictionaryWithLabelDao.getDictionariesAsList().size == 1)
        //assert(dictionaryWithLabelDao.getDictionaryWithLabel(idDict).label == null)
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
