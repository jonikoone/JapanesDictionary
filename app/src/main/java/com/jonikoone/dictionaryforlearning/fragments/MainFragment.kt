package com.jonikoone.dictionaryforlearning.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.main.MainPresenter
import com.jonikoone.dictionaryforlearning.presentation.main.MainView
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment
import moxy.presenter.InjectPresenter
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.pure.AppNavigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.*
import timber.log.Timber

class MainFragment : BaseMvpFragment(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var drawer: DrawerLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var appBar: BottomAppBar
    //private lateinit var topHost: FrameLayout

    val router: Router by inject()
    val navigatorHolder: NavigatorHolder by inject()

    private val navigator by lazy {
        object : SupportAppNavigator(context as AppCompatActivity, childFragmentManager, R.id.hostFragment) {
            override fun applyCommand(command: Command) {
                super.applyCommand(command)
                when (command) {
                    is Forward -> {
                        presenter.addState((command.screen as Screens).getActionFragment())
                    }
                    is Replace -> {
                        presenter.addState((command.screen as Screens).getActionFragment())
                    }
                    is BackTo -> {

                    }
                    is Back -> {
                        presenter.removeLastState()
                        fragmentManager.popBackStack()
                    }
                }
            }

            override fun setupFragmentTransaction(
                    command: Command,
                    currentFragment: Fragment?,
                    nextFragment: Fragment?,
                    fragmentTransaction: FragmentTransaction
            ) {
                // Fix incorrect order lifecycle callback of MainFragment
                fragmentTransaction.setReorderingAllowed(true)
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
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)
        initClickListeners(view)
        initNavigationListener(view)
        if (savedInstanceState == null) {
            router.navigateTo(Screens.HomeScreen)
            presenter.applyState()
        }
        //presenter.navigate(Screens.HomeScreen)
        //presenter.navigateIfNeeded(Screens.HomeScreen)
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
                R.id.homeFragment -> router.newRootScreen(Screens.HomeScreen)
                R.id.labelListFragment -> router.newRootScreen(Screens.LabelListScreen)
                R.id.dictionariesFragment -> router.newRootScreen(Screens.DictionaryListScreen())
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