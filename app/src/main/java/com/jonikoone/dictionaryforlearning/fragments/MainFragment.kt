package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentMainBinding
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.viewmodels.common.MainFragmentViewModel
import okhttp3.Route
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: MainFragmentViewModel by viewModel()

    val router: Router by inject()

    private lateinit var drawer: DrawerLayout
    private lateinit var drawerMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val view = binding.root

        drawer = view.findViewById(R.id.drawerLayout)
        view.findViewById<NavigationView>(R.id.mainNavigation).setNavigationItemSelectedListener(this)

        viewModel.openDrawer = View.OnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        return view
    }

    fun closeDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun navigate(screen: Screens): Boolean {
        router.navigateTo(screen)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.labelListFragment -> navigate(Screens.LabelListScreen())
            R.id.dictionariesFragment -> navigate(Screens.DictionaryListScreen())
            else -> false
        }
    }
}