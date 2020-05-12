package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.main.MainPresenter
import com.jonikoone.dictionaryforlearning.presentation.main.MainView

class MainFragment : MvpAppCompatFragment(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var drawer: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        drawer = root.findViewById(R.id.drawerLayout)

        initListeners(root)
        return root
    }


    fun initListeners(root: View) {
        root.findViewById<BottomAppBar>(R.id.bottomAppBar).setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        root.findViewById<NavigationView>(R.id.mainNavigation).setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.labelListFragment -> presenter.navigate(Screens.LabelListScreen())
                R.id.dictionariesFragment -> presenter.navigate(Screens.DictionaryListScreen())
                else -> false
            }
        }
    }


    override fun openNavigation() {
        drawer.openDrawer(GravityCompat.START)
    }

    override fun closeNavigation() {
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun clickOnFloatingActionButton() {
        TODO("Not yet implemented")
    }
}