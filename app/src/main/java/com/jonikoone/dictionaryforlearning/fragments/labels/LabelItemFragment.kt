package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.databinding.FragmentLabelBinding
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.viewmodels.labels.LabelViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Router

class LabelItemFragment(private val label: Label) : Fragment() {

    val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_label, container, false)

        view.findViewById<LinearLayout>(R.id.clickable_itemLabel).setOnClickListener {
            router.navigateTo(Screens.LabelScreen(label))
        }

        return view
    }



}

