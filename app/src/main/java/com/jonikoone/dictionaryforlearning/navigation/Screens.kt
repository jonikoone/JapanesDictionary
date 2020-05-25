package com.jonikoone.dictionaryforlearning.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.jonikoone.databasemodule.database.entites.Word
import com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaryEditFragment
import com.jonikoone.dictionaryforlearning.fragments.dictionary.LibraryFragment
import com.jonikoone.dictionaryforlearning.fragments.home.HomeFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment
import com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment
import com.jonikoone.dictionaryforlearning.fragments.words.WordsListFragment
import com.jonikoone.dictionaryforlearning.presentation.main.MainState
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens(
    private val fragment: Fragment,
    @IdRes val containerId: Int? = null
) : SupportAppScreen() {



    override fun getFragment(): Fragment? {
        return fragment
    }

    fun getActionFragment(): MainState? {
        return fragment.let {
            if (it is FragmentActionContainer) {
                it.state
            } else null
        }
    }


    object HomeScreen : Screens(HomeFragment())

    object LabelListScreen : Screens(LabelListFragment())
    class LabelScreen(labelId: Long) : Screens(LabelFragment.getInstance(labelId))

    class WordsListScreen(dictionaryId: Long) : Screens(WordsListFragment.newInstance(dictionaryId))
    class WordScreen(word: Word) : Screens(WordItemFragment(word))

    class DictionaryEditScreen(dictionaryId: Long) : Screens(DictionaryEditFragment.newInstance(dictionaryId))

    class DictionaryListScreen() : Screens(LibraryFragment())

    override fun toString(): String {
        return "{Screen: ${this.screenKey}, ${this.fragment.javaClass.canonicalName}}"
    }

}

interface FragmentActionContainer {
    val state: MainState
}



