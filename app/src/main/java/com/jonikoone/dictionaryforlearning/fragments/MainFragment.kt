package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.main.MainPresenter
import com.jonikoone.dictionaryforlearning.presentation.main.MainView

class MainFragment : MvpAppCompatFragment(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var drawer: DrawerLayout
    private lateinit var fab: FloatingActionButton

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
        initClickListeners(view)
        initNavigationListener(view)
    }


    fun initClickListeners(view: View) {
        view.findViewById<BottomAppBar>(R.id.bottomAppBar).setNavigationOnClickListener {
            presenter.toggleNavigation(true)
        }

        view.findViewById<FloatingActionButton>(R.id.mainFab).setOnClickListener {
            presenter.clickOnFab()
        }

    }

    fun initNavigationListener(view: View) {
        view.findViewById<NavigationView>(R.id.mainNavigation).setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> presenter.navigate(Screens.HomeScreen)
                R.id.labelListFragment -> presenter.navigate(Screens.LabelListScreen)
                R.id.dictionariesFragment -> presenter.navigate(Screens.DictionaryListScreen())
                else -> false
            }
        }
    }

    override fun toggleNavigation(isToggle: Boolean) {
        if (!isToggle)
            drawer.closeDrawer(GravityCompat.START)
        else
            drawer.openDrawer(GravityCompat.START)
    }

    override fun clickOnFloatingActionButton() {
        presenter.clickOnFab()
    }

    override fun setIconForFab(id: Int) {
        fab.setImageResource(id)
    }

    override fun setShowFab(isShow: Boolean) {
        when (isShow) {
            true -> fab.show()
            false -> fab.hide()
        }
    }

    override fun setLayoutAnchor(id: Int) {

    }

    override fun applyChangeState() {
        presenter.state?.let {
            setIconForFab(it.iconFab)
            setShowFab(it.isShowFab)
        }
    }
}