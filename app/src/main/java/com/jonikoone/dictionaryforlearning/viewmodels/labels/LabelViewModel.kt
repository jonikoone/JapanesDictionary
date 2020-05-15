package com.jonikoone.dictionaryforlearning.viewmodels.labels

import android.view.View
import androidx.lifecycle.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.util.*
import kotlinx.coroutines.Job
import okhttp3.internal.toHexString
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class LabelViewModel(private val labelDao: LabelDao, private var label: Label) : ViewModel(),
        SuspendWork<Label>, KoinComponent {

    val router: Router by inject()

    override var job: Job? = null
    override val delaySuspend: Long = 200

    override val work: (Label) -> Unit = {
        label = it
        labelDao.update(it)
        Timber.d("save label: $label, Color: ${label.color.toHexString()}")
    }

    private var color: Int = label.color

    val labelTitle = MutableLiveData(label.title).apply {
        observeForever {
            backgroundWork(label.copy(title = it))
        }
    }

    val stateBottomSheet = MutableLiveData(BottomSheetBehavior.STATE_HIDDEN)

    val colorListener: (Int) -> Unit = {
        backgroundWork(label.copy(color = it))
    }

    val colorListnerForStartIcon: (Int) -> Unit = {
        labelColor.value = it
    }

    val labelDifficulty = MutableLiveData(label.difficulty).apply {
        observeForever {
            backgroundWork(label.copy(difficulty = it))
        }
    }

    val labelColor = MutableLiveData(color).apply {
        observeForever {
            color = it
        }
    }

    val navigationBack = View.OnClickListener {
        router.exit()
    }

    val onClickStartIcon = View.OnClickListener {
        stateBottomSheet.value = BottomSheetBehavior.STATE_EXPANDED
    }

    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Timber.d("bs state: $newState")
            if (BottomSheetBehavior.STATE_DRAGGING == newState)
                cancelChangeColor()
        }

    }

    fun openLabelScreen() {
        router.navigateTo(Screens.LabelScreen(label))
    }

    fun cancelChangeColor() {
        stateBottomSheet.value = BottomSheetBehavior.STATE_HIDDEN
        labelColor.value = label.color
    }

    fun applyChangeColor() {
        stateBottomSheet.value = BottomSheetBehavior.STATE_HIDDEN
        backgroundWork(label.copy(color = color))
    }
}

