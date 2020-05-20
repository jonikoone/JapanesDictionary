package com.jonikoone.dictionaryforlearning.presentation.label

import com.arellomobile.mvp.MvpView

interface LabelView : MvpView {

    fun updateLabelTitle()
    fun updateLabelColor()
    fun updateLabelDifficulty()
    fun updateLabelDifficultySeek()
    fun updateShowError()

}
