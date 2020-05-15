package com.jonikoone.dictionaryforlearning.fragments.labels

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.util.mvpScope
import com.jonikoone.dictionaryforlearning.views.ColorPickerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ColorPickerBottomSheetFragmentWithDismissCallback : ColorPickerBottomSheetFragment {

    private var dismissCallback: (() -> Unit)? = null

    constructor(
        label: Label,
        dismishCallback: (() -> Unit)?
    ) : super(label) {
        this.dismissCallback = dismishCallback
    }

    override fun dismiss() {
        super.dismiss()
        dismissCallback?.invoke()
    }

}

open class ColorPickerBottomSheetFragment(private val label: Label) :
    MvpBottomSheetDialogFragment(),
    ColorPickerBottomSheetView {

    @InjectPresenter
    lateinit var presenter: ColorPickerBottomSheetPresenter

    var color: Int = label.color

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.color_picker_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.label = label
        view.findViewById<ImageButton>(R.id.cancelChange_imageButton_colorBottomSheet)
            ?.setOnClickListener {
                cancelChangeColor()
            }

        view.findViewById<ImageButton>(R.id.applyChange_imageButton_colorBottomSheet)
            ?.setOnClickListener {
                applyChangeColor()
            }

        view.findViewById<ColorPickerView>(R.id.colorPicker_colorBottomSheet)?.colorListener =
            { color ->
                changeColor(color)
            }
    }

    override fun cancelChangeColor() {
        dismiss()
    }

    override fun applyChangeColor() {
        presenter.updateColorLabel(color)
        dismiss()
    }

    fun changeColor(color: Int) {
        this.color = color
        Timber.d("new color: $color")
    }


}


interface ColorPickerBottomSheetView : MvpView {

    fun cancelChangeColor()
    fun applyChangeColor()
}

@InjectViewState
class ColorPickerBottomSheetPresenter : MvpPresenter<ColorPickerBottomSheetView>(), KoinComponent {

    val labelDao: LabelDao by inject()
    lateinit var label: Label

    fun updateColorLabel(newColor: Int) {
        mvpScope.launch(Dispatchers.IO) {
            labelDao.update(label.copy(color = newColor))
        }
    }

}

open class MvpBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var mIsStateSaved = false

    private var mMvpDelegate: MvpDelegate<out MvpBottomSheetDialogFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mIsStateSaved = false
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mIsStateSaved = true
        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }


    override fun onStop() {
        super.onStop()
        getMvpDelegate().onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate().onDetach()
        getMvpDelegate().onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        //We leave the screen and respectively all fragments will be destroyed
        if (activity!!.isFinishing) {
            getMvpDelegate().onDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (mIsStateSaved) {
            mIsStateSaved = false
            return
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        if (isRemoving || anyParentIsRemoving) {
            getMvpDelegate().onDestroy()
        }
    }

    /**
     * @return The [MvpDelegate] being used by this Fragment.
     */
    open fun getMvpDelegate(): MvpDelegate<*> {
        if (mMvpDelegate == null) {
            mMvpDelegate = MvpDelegate<MvpBottomSheetDialogFragment>(this)
        }
        return mMvpDelegate!!
    }

    //bsdf
    /**
     * Tracks if we are waiting for a dismissAllowingStateLoss or a regular dismiss once the
     * BottomSheet is hidden and onStateChanged() is called.
     */

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
        return dialog
    }


}