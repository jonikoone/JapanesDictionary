package com.jonikoone.dictionaryforlearning.presentation.main

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.fragments.home.HomeFragment
import com.jonikoone.dictionaryforlearning.navigation.Screens
import okhttp3.Route
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), KoinComponent {

    val router: Router by inject()
    val cicerone: Cicerone<Router> by inject()
    var isNavigationOpen = false

    var state: MainAction? = Screens.HomeScreen.getActionFragment()

    init {
        viewState.applyChangeState()
    }

    fun navigate(screen: Screens): Boolean {
        state = screen.getActionFragment()
        router.navigateTo(screen)
        viewState.applyChangeState()
        viewState.toggleNavigation(false)
        return true
    }

    fun exit() {
        
    }

    fun toggleNavigation(isToggle: Boolean) {
        isNavigationOpen = isToggle
        viewState.toggleNavigation(isNavigationOpen)
    }

    fun clickOnFab() {
        state?.clickOnFab?.invoke()
    }
}

data class MainAction(
    @DrawableRes val iconFab: Int = R.drawable.ic_add,
    val isShowFab: Boolean = true,
    val clickOnFab: (() -> Unit)? = null,
    @IdRes val layoutAnchor: Int? = null,
    val isShowBottomAppBar: Boolean = true
)



