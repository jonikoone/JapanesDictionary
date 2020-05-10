package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationView
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentMainBinding
import com.jonikoone.dictionaryforlearning.viewmodels.common.MainFragmentViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val view = binding.root

        return view
    }

}