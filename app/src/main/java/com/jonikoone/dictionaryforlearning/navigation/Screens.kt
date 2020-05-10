package com.jonikoone.dictionaryforlearning.navigation

import androidx.fragment.app.Fragment
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens(private val fragmentFactory: () -> Fragment) : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return fragmentFactory()
    }

    //class HomeScreen : Screens(fragmentFactory = {})

    class LabelListScreen : Screens(::LabelListFragment)
    class LabelScreen(label: Label) : Screens({ LabelItemFragment(label) })

//    class WordListScreen : Screens()
//    class WordScreen : Screens()

//    class DictionaryListScreen : Screens()
//    class DictionaryScreen : Screens()
//    class DictionaryEditScreen : Screens()
}



