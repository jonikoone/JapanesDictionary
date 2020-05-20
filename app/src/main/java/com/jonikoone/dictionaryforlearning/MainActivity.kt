package com.jonikoone.dictionaryforlearning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router

class MainActivity : AppCompatActivity(){

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
        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFragmentContainer, TestMoxyFragment())
                .commit()
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
