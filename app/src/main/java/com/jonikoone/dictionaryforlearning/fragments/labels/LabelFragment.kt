package com.jonikoone.dictionaryforlearning.fragments.labels

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.fragments.SlideFrameLayout
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.presentation.label.LabelPresenter
import com.jonikoone.dictionaryforlearning.presentation.label.LabelView
import com.jonikoone.dictionaryforlearning.presentation.main.MainAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class LabelFragment(private var editableLabel: Label) : MvpAppCompatFragment(), LabelView,
        FragmentActionContainer {


    @InjectPresenter
    lateinit var presenter: LabelPresenter

    val router: Router by inject()

    lateinit var textInputLayput: TextInputLayout
    lateinit var titleEditText: TextInputEditText
    lateinit var difficultyTitle: TextView
    lateinit var seekBar: SeekBar

    private val colorLabel = MutableLiveData<Label>(editableLabel).apply {
        observe(this@LabelFragment,
                Observer<Label> {
                    editableLabel = it
                    updateLabelColor()
                })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_label, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textInputLayput = view.findViewById(R.id.title_textInputLayout_label)
        titleEditText = view.findViewById(R.id.title_editText_label)
        difficultyTitle = view.findViewById(R.id.difficulty_textView_label)
        seekBar = view.findViewById(R.id.diffuculty_seekBar_label)

        initListeners(view)
        updateAll()
    }

    fun updateAll() {
        updateLabelTitle()
        updateLabelColor()
        updateLabelDifficulty()
        updateLabelDifficultySeek()
    }

    override fun onPause() {
        super.onPause()
        presenter.updateLabel(editableLabel)
        Timber.d("LabelFragment:onPause")
    }

    fun initListeners(view: View) {
        textInputLayput.let {

            it.setStartIconOnClickListener {
                ColorPickerBottomSheetFragmentWithDismissCallback(editableLabel) {
                    CoroutineScope(Dispatchers.Main).launch {
                        editableLabel = presenter.reloadLabel(editableLabel.id)
                        updateLabelColor()
                    }
                }
                        .show(childFragmentManager, "colorBottomSheetDialog")
            }
        }

        titleEditText.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                    ) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        editableLabel = editableLabel.copy(title = s.toString())
                        updateShowError()
                    }
                }
        )

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
            ) {
                editableLabel = editableLabel.copy(difficulty = progress)
                updateLabelDifficulty()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun updateLabelTitle() {
        titleEditText.setText(editableLabel.title.toCharArray(), 0, editableLabel.title.length)
    }

    override fun updateLabelColor() {
        textInputLayput.setStartIconTintList(
                ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(editableLabel.color))
        )
    }

    override fun updateLabelDifficulty() {
        difficultyTitle.text = "Difficulty: ${editableLabel.difficulty}"
    }

    override fun updateLabelDifficultySeek() {
        seekBar.progress = editableLabel.difficulty
    }

    override fun updateShowError() {
        textInputLayput.error =
                if (editableLabel.title.isEmpty())
                    "label should have a title!"
                else null
    }

    override val action = MainAction(
            isShowAppBar = false,
            isShowFab = false
    )

}

