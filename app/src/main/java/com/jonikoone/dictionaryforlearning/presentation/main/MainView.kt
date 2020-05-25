package com.jonikoone.dictionaryforlearning.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.jonikoone.dictionaryforlearning.navigation.Screens
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

// описываются экшены которые точно описаны в активити
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun setIconForFab(@DrawableRes id: Int)

    fun setShowFab(isShow: Boolean)

    fun clickOnFloatingActionButton()

    fun bottomAppBar(isShow: Boolean)

    fun navigate(screen: Screens)
}