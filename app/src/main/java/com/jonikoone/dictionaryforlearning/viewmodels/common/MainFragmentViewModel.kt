package com.jonikoone.dictionaryforlearning.viewmodels.common

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {

    val titleScreen = MutableLiveData<String>()

    val navigationClick = View.OnClickListener {

    }
}