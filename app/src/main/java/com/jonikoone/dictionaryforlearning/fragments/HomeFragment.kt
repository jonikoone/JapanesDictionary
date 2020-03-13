package com.jonikoone.dictionaryforlearning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.databinding.FragmentHomeBinding
import com.jonikoone.dictionaryforlearning.viewmodels.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class HomeFragment : Fragment(), KoinComponent {

    private val viewModelHome: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModelHome
        binding.lifecycleOwner = this


        return binding.root
    }

}
