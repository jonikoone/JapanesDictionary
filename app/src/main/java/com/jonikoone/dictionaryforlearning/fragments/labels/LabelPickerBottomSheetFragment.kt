package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.views.ColorPickerView
import moxy.MvpBottomSheetDialogFragment

class LabelPickerBottomSheetFragment(private val dismissCallback: (Label) -> Unit)
    : MvpBottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.label_picker_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}