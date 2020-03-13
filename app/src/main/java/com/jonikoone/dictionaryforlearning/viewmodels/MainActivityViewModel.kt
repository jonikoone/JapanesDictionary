package com.jonikoone.dictionaryforlearning.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

class MainActivityViewModel : ViewModel(), KoinComponent {

    private var functionClick: (() -> Unit) = {
        Log.e("Test", "click!")
    }

    fun onClick() {
        functionClick?.invoke()
    }

    fun setFabOnClick(onClick: () -> Unit) {
        this.functionClick = onClick
    }

}