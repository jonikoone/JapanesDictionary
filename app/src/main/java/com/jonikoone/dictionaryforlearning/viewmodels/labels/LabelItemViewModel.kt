package com.jonikoone.dictionaryforlearning.viewmodels.labels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.navigation.Screens
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Router

class LabelItemViewModel(private val label: Label) : ViewModel(), KoinComponent{

    val router: Router by inject()

    val labelTitle: LiveData<String> = MutableLiveData(label.title)

    val labelDifficulty: LiveData<Int> = MutableLiveData(label.difficulty)

    val labelColor: LiveData<Int> = MutableLiveData(label.color)


    fun openLabelScreen() {
        router.navigateTo(Screens.LabelScreen(label))
    }

}