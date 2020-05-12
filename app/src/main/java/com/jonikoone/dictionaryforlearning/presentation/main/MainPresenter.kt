package com.jonikoone.dictionaryforlearning.presentation.main

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Router

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), KoinComponent {


    val router: Router by inject()




    fun navigate(screen: Screens): Boolean {
        router.navigateTo(screen)
        viewState.closeNavigation()
        return true
    }


}