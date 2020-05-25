package com.jonikoone.dictionaryforlearning.util

import moxy.MvpPresenter
import moxy.MvpView
import timber.log.Timber

const val TAG_BASE_PRESENTER = "BasePresenter:Log:"

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Timber.d("$TAG_BASE_PRESENTER onFirstViewAttach $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$TAG_BASE_PRESENTER onDestroy $this")
    }

}