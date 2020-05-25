package com.jonikoone.dictionaryforlearning.util

import android.os.Bundle
import moxy.MvpAppCompatFragment
import timber.log.Timber

private const val TAG_BASE_FRAGMENT = "BaseMvpFragment:Log:"

abstract class BaseMvpFragment : MvpAppCompatFragment() {

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("$TAG_BASE_FRAGMENT onSaveInstanceState $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$TAG_BASE_FRAGMENT onDestroy $this")
    }


}