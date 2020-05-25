package com.jonikoone.dictionaryforlearning.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.drawerlayout.widget.DrawerLayout
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.util.BasePresenter
import moxy.InjectViewState
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

interface MainStateHolder {
    val states: MutableList<MainState?>

    fun clickOnFab() {
        states.last()?.clickOnFab?.invoke()
    }

    fun addState(state: MainState?) {
        states += state
        states.last()?.let { applyState(it) }
    }

    fun removeLastState() {
        states -= states.last()
        states.last()?.let { applyState(it) }
    }

    fun applyState(state: MainState? = states.last()) {

    }
}

@InjectViewState
class MainPresenter : BasePresenter<MainView>(), KoinComponent, MainStateHolder {

    val router: Router by inject()
    val cicerone: Cicerone<Router> by inject()

    override val states = mutableListOf<MainState?>(Screens.HomeScreen.getActionFragment())

    override fun applyState(state: MainState?) {
        state?.let {
            viewState.setIconForFab(it.iconFab)
            viewState.setShowFab(it.isShowFab)
            viewState.bottomAppBar(it.isShowAppBar)
        }
    }

    fun navigate(screen: Screens) {
        viewState.navigate(screen)
        //addState(screen.getActionFragment())
    }

    fun navigateIfNeeded(screen: Screens) {
        if (states.size == 0)
            navigate(screen)
    }
}

data class MainState(
        @DrawableRes val iconFab: Int = R.drawable.ic_add,
        val isShowFab: Boolean = true,
        val clickOnFab: (() -> Unit)? = null,
        @IdRes val layoutAnchor: Int? = null,
        val isShowBottomAppBar: Boolean = true,
        val lockModeTopHostFragment: Int = DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
        val isShowAppBar: Boolean = true
)