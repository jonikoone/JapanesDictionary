package com.jonikoone.dictionaryforlearning.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.presentation.main.MainState
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment

class HomeFragment : BaseMvpFragment(), FragmentActionContainer {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override val state: MainState
        get() = MainState(isShowFab = false, layoutAnchor = null)

}
