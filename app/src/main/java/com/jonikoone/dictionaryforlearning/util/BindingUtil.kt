package com.jonikoone.dictionaryforlearning.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
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
    @BindingAdapter("app:setState")
    fun setState(view: View, state: Int) {
        BottomSheetBehavior.from(view).state = state
    }

    @JvmStatic
    @BindingAdapter("app:addBottomCallback")
    fun addBottomCallback(view: View, callback: BottomSheetBehavior.BottomSheetCallback) {
        BottomSheetBehavior.from(view).addBottomSheetCallback(callback)
    }

    @JvmStatic
    @BindingAdapter("app:onLongClick") //TODO: mb refactoring to app:setLongClick
    fun onLongClick(view: View, listener: View.OnLongClickListener) {
        view.setOnLongClickListener(listener)
    }

    @JvmStatic
    @BindingAdapter("app:setClick")
    fun setOnClickListener(view: View, listener: View.OnClickListener) {
        view.setOnClickListener(listener)
    }

    @JvmStatic
    @BindingAdapter("app:colorTint")
    fun setColorTint(view: View, color: Int) {
        view.backgroundTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color))
    }

    @JvmStatic
    @BindingAdapter("app:loadStateListAnimation")
    fun loadStateListAnimation(view: View, idRes: Int) {

    }

    @JvmStatic
    @BindingAdapter("app:setSelectionTracker")
    fun setSelectionTracker(recyclerView: RecyclerView, createSelectionTracker: (RecyclerView) -> Unit) {
        createSelectionTracker(recyclerView)
    }

    @JvmStatic
    @BindingAdapter("app:setStateRotationAnimation")
    fun setStateRotationAnimation(view: View, rotation: Boolean) {
        view.animate().apply {
            duration = 200
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                }
            })
            rotation(if (rotation) 135f else 0f)

        }
    }

    @JvmStatic
    @BindingAdapter("app:setItemDecoration")
    fun setItemDecoration(recyclerView: RecyclerView, decorartionFactory: (RecyclerView) -> RecyclerView.ItemDecoration) {
        recyclerView.addItemDecoration(decorartionFactory(recyclerView))
    }

    @JvmStatic
    @BindingAdapter("app:setNavigationOnClickListener")
    fun setNavigationOnClickListener(bottomAppBar: BottomAppBar, clickListener: View.OnClickListener) {
        bottomAppBar.setNavigationOnClickListener(clickListener)
    }

}