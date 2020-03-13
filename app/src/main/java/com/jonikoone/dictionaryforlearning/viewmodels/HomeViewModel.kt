package com.jonikoone.dictionaryforlearning.viewmodels

import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.entites.Word
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class HomeViewModel : ViewModel(), KoinComponent {

    var onClickListener: (() -> Unit)? = null

    fun onClick() {
        onClickListener?.invoke()
    }

}