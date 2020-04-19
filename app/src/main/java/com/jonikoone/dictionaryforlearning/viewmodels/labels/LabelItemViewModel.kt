package com.jonikoone.dictionaryforlearning.viewmodels.labels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment

class LabelItemViewModel(private val label: Label) : ViewModel() {

    val labelTitle: LiveData<String> = MutableLiveData(label.title)

    val labelDifficulty: LiveData<Int> = MutableLiveData(label.difficulty)

    val labelColor: LiveData<Int> = MutableLiveData(label.color)

    private fun createArgs() = Bundle().apply {
        putSerializable(LabelItemFragment.LABEL, label)
    }

    fun openLabelScreen() {
        NavScreens.navController.navigate(R.id.labelEditFragment, createArgs())
    }

}