package com.jonikoone.dictionaryforlearning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jonikoone.dictionaryforlearning.fragments.MainFragment
import moxy.MvpAppCompatActivity
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class MainActivity : MvpAppCompatActivity(){

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
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainFragmentContainer, MainFragment())
                    .commit()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$TAG onDestroyActivity $this")
    }

    override fun onBackPressed() {
        router.exit()
        //super.onBackPressed()
    }
}
