package com.jonikoone.dictionaryforlearning.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jonikoone.dictionaryforlearning.navigation.Screens

// описываются экшены которые точно описаны в активити
interface MainView : MvpView {

    fun setIconForFab(@DrawableRes id: Int)

    fun setShowFab(isShow: Boolean)

    fun clickOnFloatingActionButton()

    fun bottomAppBar(isShow: Boolean)

    @StateStrategyType(value = AddToEndStrategy::class)
    fun navigate(screen: Screens)
}