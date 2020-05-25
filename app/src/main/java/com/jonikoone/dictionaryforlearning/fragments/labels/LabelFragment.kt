package com.jonikoone.dictionaryforlearning.fragments.labels

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.os.bundleOf
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.presentation.label.LabelPresenter
import com.jonikoone.dictionaryforlearning.presentation.label.LabelView
import com.jonikoone.dictionaryforlearning.presentation.main.MainState
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment
import com.jonikoone.dictionaryforlearning.util.colorStatelistOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.presenter.InjectPresenter
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class LabelFragment() : BaseMvpFragment(), LabelView,
        FragmentActionContainer {

    val labelDao: LabelDao by inject()

    val labelId: Long by lazy { arguments?.getLong(ARG_LABELID) as Long }

    companion object {
        val ARG_LABELID = "${this::class.qualifiedName}::labelId"
        fun getInstance(labelId: Long) = LabelFragment().apply {
            arguments = bundleOf(ARG_LABELID to labelId)
        }
    }

    @InjectPresenter
    lateinit var presenter: LabelPresenter

    fun provigePresenter() = LabelPresenter(labelDao, labelId)

    val router: Router by inject()

    lateinit var textInputLayput: TextInputLayout
    lateinit var titleEditText: TextInputEditText
    lateinit var difficultyTitle: TextView
    lateinit var seekBar: SeekBar

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
        labelId?.let {
            presenter.loadLabel(it)
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("LabelFragment:onPause")
    }

    fun initListeners(view: View) {
        textInputLayput.setStartIconOnClickListener {
            ColorPickerBottomSheetFragment() { color ->
                CoroutineScope(Dispatchers.Main).launch {
                    presenter.updateColor(color)
                }
            }
                    .show(childFragmentManager, "colorBottomSheetDialog")
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
                        presenter.updateTitle(s.toString())
                    }
                }
        )

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
            ) {
                presenter.updateDifficulty(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun updateLabelTitle(title: String) {
        val chars = title.toCharArray()
        titleEditText.setText(chars, 0, chars.size)
    }

    override fun updateLabelColor(color: Int) {
        textInputLayput.setStartIconTintList(colorStatelistOf(color))
    }

    override fun updateLabelDifficulty(difficulty: Int) {
        seekBar.progress = difficulty
    }

    override fun updateLabelDifficultyText(difficulty: Int) {
        difficultyTitle.text = "Difficulty: $difficulty"
    }

    override val state = MainState(
            isShowAppBar = false,
            isShowFab = false
    )

}