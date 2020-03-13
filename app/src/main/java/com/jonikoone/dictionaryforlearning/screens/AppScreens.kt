package com.jonikoone.dictionaryforlearning.screens

import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.fragments.HomeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class AppScreens(private val fragment: Fragment) : SupportAppScreen() {
    override fun getFragment() = fragment
}

object HomeScreen : AppScreens(HomeFragment())
