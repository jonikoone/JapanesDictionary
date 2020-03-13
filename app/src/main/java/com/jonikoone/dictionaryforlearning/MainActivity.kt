package com.jonikoone.dictionaryforlearning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jonikoone.dictionaryforlearning.screens.HomeScreen
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MainActivity : AppCompatActivity() {

    val cicerone: Cicerone<Router> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cicerone.router.newRootScreen(HomeScreen)

    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(get { parametersOf(this) })
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.removeNavigator()
    }
}