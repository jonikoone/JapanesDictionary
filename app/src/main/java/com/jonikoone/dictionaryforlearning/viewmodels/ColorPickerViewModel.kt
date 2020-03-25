package com.jonikoone.dictionaryforlearning.viewmodels

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.jonikoone.dictionaryforlearning.NavScreens
import com.jonikoone.databasemodule.database.entites.Label
import okhttp3.internal.toHexString
import timber.log.Timber

class ColorPickerViewModel(private val label: com.jonikoone.databasemodule.database.entites.Label, private val color: Int = label.color) : ViewModel() {

    init {
        Timber.d("init color: ${color.toHexString()}")
    }

    val redColor = MutableLiveData((color shr 16) and 0xff )
    val greenColor = MutableLiveData((color shr 8) and 0xff )
    val blueColor = MutableLiveData(color and 0xff )

    val colorLabel = MutableLiveData(color).also {
        redColor.observeForever { calculateBaseColor() }
        greenColor.observeForever { calculateBaseColor() }
        blueColor.observeForever { calculateBaseColor() }
    }

    private fun calculateBaseColor() {
        val red = redColor.value ?: 0
        val green = greenColor.value ?: 0
        val blue = blueColor.value ?: 0
        colorLabel?.value = (0xff000000 + (((red shl 16) ) + ((green shl 8) ) + blue)).toInt()
        Timber.d("calc color: ${colorLabel?.value?.toHexString()}")
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:onClickIconNavigation")
        fun setListener(toolbar: MaterialToolbar, listener: View.OnClickListener) {
            toolbar.setNavigationOnClickListener(listener)
        }
    }

    val navigationBack = View.OnClickListener {
        NavScreens.navController.popBackStack()
    }

}