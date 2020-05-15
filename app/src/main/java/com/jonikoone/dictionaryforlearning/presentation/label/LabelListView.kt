package com.jonikoone.dictionaryforlearning.presentation.label

import com.arellomobile.mvp.MvpView
import com.jonikoone.databasemodule.database.entites.Label

interface LabelListView : MvpView {
    fun clickOnAddLabel()
    fun clickOnLabel(label: Label)

    fun updateList(labelList: List<Label>)
}