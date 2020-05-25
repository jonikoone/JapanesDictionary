package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.views.ColorPickerView

open class ColorPickerBottomSheetFragment(private val dismissCallback: ((color: Int) -> Unit)? = null) :
    BottomSheetDialogFragment() {

    var color: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.color_picker_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<ImageButton>(R.id.cancelChange_imageButton_colorBottomSheet)
            ?.setOnClickListener {
                dismiss()
            }

        view.findViewById<ImageButton>(R.id.applyChange_imageButton_colorBottomSheet)
            ?.setOnClickListener {
                color?.let {
                    dismissCallback?.invoke(it)
                }

                dismiss()
            }

        view.findViewById<ColorPickerView>(R.id.colorPicker_colorBottomSheet)?.colorListener =
            { color ->
                this.color = color
            }
    }
}