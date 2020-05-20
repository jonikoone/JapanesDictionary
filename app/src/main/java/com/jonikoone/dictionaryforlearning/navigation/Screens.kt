package com.jonikoone.dictionaryforlearning.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaryEditFragment
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaryListFragment
import com.jonikoone.dictionaryforlearning.fragments.home.HomeFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment
import com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment
import com.jonikoone.dictionaryforlearning.fragments.words.WordsListFragment
import com.jonikoone.dictionaryforlearning.presentation.main.MainAction
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens(
    private val fragment: Fragment,
    @IdRes val containerId: Int? = null
) : SupportAppScreen() {



    override fun getFragment(): Fragment? {
        return fragment
    }

    fun getActionFragment(): MainAction? {
        return fragment.let {
            if (it is FragmentActionContainer) {
                it.action
            } else null
        }
    }


    object HomeScreen : Screens(HomeFragment())

    object LabelListScreen : Screens(LabelListFragment())
    class LabelScreen(label: Label) : Screens(LabelFragment(label))

    class WordsListScreen(dictionary: Dictionary) : Screens(WordsListFragment(dictionary))
    class WordScreen(word: Word) : Screens(WordItemFragment(word))

    class DictionaryEditScreen(dictionary: Dictionary) : Screens(DictionaryEditFragment(dictionary))

    class DictionaryListScreen() : Screens(DictionaryListFragment())

    override fun toString(): String {
        return "{Screen: ${this.screenKey}, ${this.fragment.javaClass.canonicalName}}"
    }

}

interface FragmentActionContainer {
    val action: MainAction
}



