package com.jonikoone.dictionaryforlearning.util

import android.content.res.ColorStateList
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber

object BindingUtil {

    @JvmStatic
    @BindingAdapter("app:setTintColorStartIcon")
    fun setTintColorStartIcon(textInputLayout: TextInputLayout, color: Int) {
        textInputLayout.setStartIconTintList(ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color)))
    }

    @JvmStatic
    @BindingAdapter("app:setOnClickStartIcon")
    fun setOnClickListenerStartIcon(textInputLayout: TextInputLayout, listener: View.OnClickListener) {
        Timber.d("setListener")
        textInputLayout.setStartIconOnClickListener(listener)
    }

    @JvmStatic
    @BindingAdapter("add:setState")
    fun setState(view: View, state: Int) {
        BottomSheetBehavior.from(view).state = state
    }

    @JvmStatic
    @BindingAdapter("add:addBottomCallback")
    fun addBottomCallback(view: View, callback: BottomSheetBehavior.BottomSheetCallback) {
        BottomSheetBehavior.from(view).addBottomSheetCallback(callback)
    }


}