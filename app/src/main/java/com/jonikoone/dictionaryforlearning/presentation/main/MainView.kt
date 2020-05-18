package com.jonikoone.dictionaryforlearning.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

// описываются экшены которые точно описаны в активити
interface MainView : MvpView {

    //action items
    fun setIconForFab(@DrawableRes id: Int)
    fun setShowFab(isShow: Boolean)
    fun clickOnFloatingActionButton()
    fun setLayoutAnchor(@IdRes id: Int)

    fun applyChangeState()

}