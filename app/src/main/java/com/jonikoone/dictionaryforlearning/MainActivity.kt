package com.jonikoone.dictionaryforlearning

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.fragments.MainFragment
import com.jonikoone.dictionaryforlearning.navigation.Screens
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainActivity : AppCompatActivity() {

    private val router: Router by inject()
    /*
    private val navigationHolder: NavigatorHolder by inject()*/

    /*private val navigation: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.hostFragment) {
            override fun createFragment(screen: SupportAppScreen): Fragment? {
                return super.createFragment(screen)
            }
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //router.newRootScreen(Screens.HomeScreen)
    }

    /*override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigation)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }*/


    override fun onBackPressed() {
        router.exit()
        //super.onBackPressed()
    }
}