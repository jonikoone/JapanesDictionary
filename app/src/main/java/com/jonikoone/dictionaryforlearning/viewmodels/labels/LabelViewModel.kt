package com.jonikoone.dictionaryforlearning.viewmodels.labels

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.database.entites.Label
import com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import timber.log.Timber

class LabelViewModel(var label: Label) : ViewModel(), KoinComponent {



    val labelTitle: MutableLiveData<String> = MutableLiveData(label.title)

    val labelDifficulty: MutableLiveData<Byte> = MutableLiveData(label.difficulty)

    val labelColor: MutableLiveData<Int> = MutableLiveData(label.color)


    /*@BindingAdapter("app:myClick")
    fun clickBinding(v: View, i: Int) {
        this.v = v
    }*/

    fun openLabelScreen() {
        val args = Bundle().apply {
            putSerializable(LabelItemFragment.LABEL_ARG, label)
        }
        NavScreens.navController.navigate(R.id.labelItemFragment, args)
    }

    fun openColorPeackerDialog() {
        NavScreens.navController.navigate(R.id.dialogColorPeacker)
    }

    init {
        Timber.d("label view model: init label $this")
    }

}

