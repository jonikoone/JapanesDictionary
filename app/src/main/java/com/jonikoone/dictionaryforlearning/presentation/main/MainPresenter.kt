package com.jonikoone.dictionaryforlearning.presentation.main

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.drawerlayout.widget.DrawerLayout
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), KoinComponent {

    val router: Router by inject()
    val cicerone: Cicerone<Router> by inject()
    var isNavigationOpen = false

    val stateStack = mutableListOf<MainAction?>()

    var state: MainAction? = Screens.HomeScreen.getActionFragment()

    init {
        viewState.applyChangeState()
        navigationManager = this
    }

    fun clickOnFab() {
        state?.clickOnFab?.invoke()
    }

    fun addState(action: MainAction?) {
        stateStack.add(action)
        state = stateStack.last()
        viewState.applyChangeState()
    }

    fun removeLastState() {
        stateStack.remove(stateStack.last())
        state = stateStack.last()
        viewState.applyChangeState()
    }
}

data class MainAction(
    @DrawableRes val iconFab: Int = R.drawable.ic_add,
    val isShowFab: Boolean = true,
    val clickOnFab: (() -> Unit)? = null,
    @IdRes val layoutAnchor: Int? = null,
    val isShowBottomAppBar: Boolean = true,
    val lockModeTopHostFragment: Int = DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
    val isShowAppBar: Boolean = true
)

//TODO: Fix this solution
var navigationManager: MainPresenter? = null

