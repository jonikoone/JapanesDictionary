package com.jonikoone.dictionaryforlearning.presentation.label

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LabelView : MvpView {

    fun updateLabelTitle(title: String)
    fun updateLabelColor(color: Int)
    fun updateLabelDifficulty(difficulty: Int)
    fun updateLabelDifficultyText(difficulty: Int)

}
