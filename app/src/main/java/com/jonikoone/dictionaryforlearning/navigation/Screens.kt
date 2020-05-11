package com.jonikoone.dictionaryforlearning.navigation

import androidx.fragment.app.Fragment
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaryEditFragment
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaryListFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment
import com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment
import com.jonikoone.dictionaryforlearning.fragments.words.WordsListFragment
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens(private val fragmentFactory: () -> Fragment) : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return fragmentFactory()
    }

    //class HomeScreen : Screens(fragmentFactory = {})

    class LabelListScreen : Screens(::LabelListFragment)
    class LabelScreen(label: Label) : Screens({ LabelItemFragment(label) })

    class WordsListScreen(dictionary: Dictionary) : Screens({ WordsListFragment(dictionary) })
    class WordScreen(word: Word) : Screens({ WordItemFragment(word) })

    class DictionaryEditScreen(dictionary: Dictionary) : Screens({ DictionaryEditFragment(dictionary) })
    class DictionaryListScreen() : Screens(::DictionaryListFragment)


//    class DictionaryScreen : Screens()
//    class DictionaryEditScreen : Screens()
}



