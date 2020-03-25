package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.databasemodule.database.entites.Dictionary


class DictionaryItemFragmet : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_dictionary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}

class DictionaryItemViewModel(private val dict: com.jonikoone.databasemodule.database.entites.Dictionary) : ViewModel() {

    val titleDict: LiveData<String> = MutableLiveData(dict.title)
    val labelTint: LiveData<Int> = MutableLiveData(Color.BLUE)

    fun openDict() {

    }

    @BindingAdapter("colorLabel")
    fun colorLabel(view: View, color: Int) {
        view is ImageView

    }
}
