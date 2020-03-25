package com.jonikoone.dictionaryforlearning.viewmodels.labels

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.AppDatabase
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment
import com.jonikoone.dictionaryforlearning.viewmodels.words.BackgroundSave
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class LabelViewModel(private var label: Label) : ViewModel(), KoinComponent, BackgroundSave<Label> {

    private val database: AppDatabase by inject()

    override var job: Job? = null
    override val delaySave: Long = 500
    override val saveBlock: (Label) -> Unit = {
        database.getLabelDao().updateLabel(it)
    }

    var title: String = label.title

    val labelTitle = MutableLiveData(label.title).apply {
        observeForever {
            label = label.copy(title = it)
            backgroundSave(label)
        }
    }

    val labelDifficultyText by lazy { MutableLiveData("Difficulty is ${label.difficulty}") }

    val labelDifficulty = MutableLiveData(label.difficulty).apply {
        observeForever {
            labelDifficultyText.value = "Difficulty is $it"
            label = label.copy(difficulty = it)
            backgroundSave(label)
        }
    }

    val labelColor = MutableLiveData(label.color)

    private val args = Bundle().apply {
        putSerializable(LabelItemFragment.LABEL_ARG, label)
    }

    fun openLabelScreen() {

        NavScreens.navController.navigate(R.id.labelItemFragment, args)
    }

    fun openColorPeackerDialog() {
        NavScreens.navController.navigate(R.id.dialogColorPicker, args)

    }

    init {
        Timber.d("label view model: init label $this")
    }

    /*@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun autoSaveLabel() = viewModelScope.launch {
        val newLabel = label.copy(
            title = title,
            difficulty = labelDifficulty.value!!,
            color = labelColor.value!!
        )
        Timber.d("navigation back: label = $newLabel")
        withContext(Dispatchers.IO) {
            database.getLabelDao().updateLabel(newLabel)
        }
    }*/


    val navigationBack = View.OnClickListener {
        NavScreens.navController.popBackStack()
    }

    val listener = object : NumberPicker.OnValueChangeListener{
        override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
            labelDifficulty.value = picker?.value
        }

    }

    companion object {

        /*@JvmStatic
        @BindingAdapter("app:onValueChange")
        fun onValueChange(numberPicker: NumberPicker, listener: NumberPicker.OnValueChangeListener) {
            numberPicker.setOnValueChangedListener(listener)
        }*/
    }

}

object ViewModelBindingAdapters {

}

