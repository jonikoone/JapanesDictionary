package com.jonikoone.dictionaryforlearning.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.koin.core.KoinComponent

abstract class BaseFragment : Fragment(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun <T : ViewDataBinding> createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutId: Int
    ): T {
        val binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        //binding.viewModel = viewModel
        return binding
    }

}