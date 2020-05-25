package com.jonikoone.dictionaryforlearning.presentation.label

import com.jonikoone.databasemodule.database.entites.Label
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LabelListView : MvpView {
    fun updateList(labelList: List<Label>)
}