package com.jonikoone.dictionaryforlearning.viewmodels

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.internal.toHexString
import timber.log.Timber

class ColorPeackerViewModel(private val color: Int = Color.GREEN) : ViewModel() {

    init {
        Timber.d("init color: ${color.toHexString()}")
    }

    val redColor = MutableLiveData((color shr 16) and 0xff )
    val greenColor = MutableLiveData((color shr 8) and 0xff )
    val blueColor = MutableLiveData(color and 0xff )

    val colorCard = MutableLiveData(color).also {
        redColor.observeForever { calculateBaseColor() }
        greenColor.observeForever { calculateBaseColor() }
        blueColor.observeForever { calculateBaseColor() }
    }




    private fun calculateBaseColor() {
        val red = redColor.value ?: 0
        val green = greenColor.value ?: 0
        val blue = blueColor.value ?: 0
        colorCard?.value = (0xff000000 + (((red shl 16) ) + ((green shl 8) ) + blue)).toInt()
        Timber.d("calc color: ${colorCard?.value?.toHexString()}")
    }

}