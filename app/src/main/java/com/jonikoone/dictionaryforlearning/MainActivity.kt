package com.jonikoone.dictionaryforlearning

import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    private val cicerone: Cicerone<Router> by inject()
    private val navigationHolder: NavigatorHolder by inject()

    private val navigation: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.hostFragment) {



        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cicerone.router.newRootScreen(Screens.LabelScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigation)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

}