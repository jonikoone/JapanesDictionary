package com.jonikoone.dictionaryforlearning.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.presentation.main.MainAction
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : MvpAppCompatFragment(), FragmentActionContainer {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override val action: MainAction
        get() = MainAction(isShowFab = false, layoutAnchor = null)

}
