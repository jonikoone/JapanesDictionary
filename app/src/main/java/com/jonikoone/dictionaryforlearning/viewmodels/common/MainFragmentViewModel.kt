package com.jonikoone.dictionaryforlearning.viewmodels.common

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {

    val mainFragmentState = MainFragmentState.LabelListState

    val titleScreen = MutableLiveData<String>()

    var openDrawer: View.OnClickListener? = null

    val navigationClick = View.OnClickListener {

    }
}

sealed class MainFragmentState : State() {

    object LabelListState : MainFragmentState() {

    }

}



open class State() {

}

