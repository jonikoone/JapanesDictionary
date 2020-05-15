package com.jonikoone.dictionaryforlearning.util

import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val MvpPresenter<*>.mvpScope: CoroutineScope
    get() = CoroutineScope(Dispatchers.Main)