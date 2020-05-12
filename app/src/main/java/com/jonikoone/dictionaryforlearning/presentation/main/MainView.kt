package com.jonikoone.dictionaryforlearning.presentation.main

import com.arellomobile.mvp.MvpView

// описываются экшены которые точно описаны в активити
interface MainView : MvpView {

    fun openNavigation()
    fun closeNavigation()

    fun clickOnFloatingActionButton()

}