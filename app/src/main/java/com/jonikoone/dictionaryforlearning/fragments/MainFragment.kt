package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.main.MainPresenter
import com.jonikoone.dictionaryforlearning.presentation.main.MainView
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.*
import timber.log.Timber

class MainFragment : MvpAppCompatFragment(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var drawer: DrawerLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var appBar: BottomAppBar
    //private lateinit var topHost: FrameLayout

    val router: Router by inject()
    val navigatorHolder: NavigatorHolder by inject()

    private val navigator by lazy {
        object : Navigator {
            @IdRes
            val mainHostFragment: Int = R.id.hostFragment

            //val othersHostFragment: IntArray = intArrayOf(R.id.topHostFragment)
            val fm = childFragmentManager

            override fun applyCommands(commands: Array<out Command>) {
                fm.executePendingTransactions()

                for (command in commands) {
                    try {
                        when (command) {
                            is Forward -> {
                                val screen = command.screen as Screens
                                //Timber.d("MainFragment: Navigator implementation: screen: $screen")
                                //presenter.addState(screen.getActionFragment())

                                screen.fragment?.let {
                                    val containerId = mainHostFragment
                                    fm.beginTransaction()
                                            .replace(containerId, it)
                                            .addToBackStack(screen.screenKey)
                                            .commit()
                                }
                            }
                            is Replace -> {
                                //activityReplace(command)
                            }
                            is BackTo -> {
                                //backTo(command)
                            }
                            is Back -> {
                                presenter.removeLastState()
                                fm.popBackStack()
                            }
                        }
                    } catch (e: RuntimeException) {
                        //errorOnApplyCommand(command, e)
                    }
                }
            }

        }
    }

    override fun navigate(screen: Screens) {
        Timber.d("navigate to ${screen.screenKey}")
        router.navigateTo(screen)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        drawer = view.findViewById(R.id.drawerLayout)
        fab = view.findViewById(R.id.mainFab)
        appBar = view.findViewById(R.id.bottomAppBar)
        //topHost = view.findViewById(R.id.topHostFragment)
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)
        initClickListeners(view)
        initNavigationListener(view)
        //presenter.navigate(Screens.HomeScreen)
        presenter.navigateIfNeeded(Screens.HomeScreen)
    }


    fun initClickListeners(view: View) {
        appBar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        fab.setOnClickListener {
            presenter.clickOnFab()
        }

    }

    fun initNavigationListener(view: View) {
        view.findViewById<NavigationView>(R.id.mainNavigation).setNavigationItemSelectedListener { item ->
            drawer.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.homeFragment -> presenter.navigate(Screens.HomeScreen)
                R.id.labelListFragment -> presenter.navigate(Screens.LabelListScreen)
                R.id.dictionariesFragment -> presenter.navigate(Screens.DictionaryListScreen())
            }
            true
        }
    }

    override fun clickOnFloatingActionButton() {
        presenter.clickOnFab()
    }

    override fun setIconForFab(id: Int) {
        fab.setImageResource(id)
    }

    override fun setShowFab(isShow: Boolean) {
        if (isShow) fab.show()
        else fab.hide()
    }



    override fun bottomAppBar(isShow: Boolean) {
        /*val behavior = appBar.behavior as HideBottomViewOnScrollBehavior<BottomAppBar>
        if (isShow) behavior.slideUp(appBar)
        else behavior.slideDown(appBar)*/
        if (isShow) {
            appBar.performShow()
        } else
            appBar.performHide()
    }

}