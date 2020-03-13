package com.jonikoone.dictionaryforlearning.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.adapters.DictionaryAdapter
import com.jonikoone.dictionaryforlearning.entites.Word
import com.jonikoone.dictionaryforlearning.screens.AppScreens
import com.jonikoone.dictionaryforlearning.screens.HomeScreen
import com.jonikoone.dictionaryforlearning.viewmodels.DictionaryViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.HomeViewModel
import com.jonikoone.dictionaryforlearning.viewmodels.ItemFragmentViewModel
import org.jetbrains.annotations.NotNull
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen

val viewModelModules = module {

    viewModel {
        HomeViewModel()
    }

    viewModel {
        DictionaryViewModel()
    }

    viewModel { (word: Word) -> ItemFragmentViewModel(word) }

    factory {
//        WordViewModel()
    }

}

val navigationModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single<Navigator> { (activity: FragmentActivity) ->
        {
            object : SupportAppNavigator(
                activity,
                R.id.fragmentContainer
            ) {
                override fun createFragment(screen: @NotNull SupportAppScreen): Fragment? {
                    screen as AppScreens
                    return when (screen) {
                        is HomeScreen -> screen.fragment
                    }
                }
            }
        }()

    }
}

val adaptersModule = module {
    single {
        DictionaryAdapter()
    }
}