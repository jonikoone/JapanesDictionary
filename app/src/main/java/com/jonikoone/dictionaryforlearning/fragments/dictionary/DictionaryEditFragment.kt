package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.TAG
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment
import com.jonikoone.dictionaryforlearning.util.colorStatelistOf
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import okhttp3.internal.toHexString
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber
import kotlin.random.Random

class DictionaryEditFragment() : BaseMvpFragment(), DictionaryEditView {

    val router: Router by inject()
    val dictionaryDao: DictionaryDao by inject()

    val dictionaryId: Long by lazy { arguments?.getLong(ARG_DICT_ID) as Long }

    @InjectPresenter
    lateinit var presenter: DictionaryEditPresenter

    @ProvidePresenter
    fun providePresenter() = DictionaryEditPresenter(dictionaryDao, router, dictionaryId)

    lateinit var titleDict: TextInputEditText
    lateinit var chooseLabel: MaterialButton

    companion object {
        val ARG_DICT_ID = "${this::class.qualifiedName}::dictionary_id"
        fun newInstance(dictId: Long) = DictionaryEditFragment().apply {
            arguments = bundleOf(ARG_DICT_ID to dictId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dictionary_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleDict = view.findViewById(R.id.dictionaryEdit_editText_title)
        chooseLabel = view.findViewById(R.id.dictionaryEdit_button_chooseLabel)
        chooseLabel.setOnClickListener {
            presenter.generateLabelColor()
        }
    }

    override fun updateTitle(title: String) {
    }

    override fun updateLabelTitle(labelTitle: String) {
    }

    override fun updateLabelColor(color: Int) {
        chooseLabel.icon = context?.getDrawable(R.drawable.ic_label)
        chooseLabel.iconTint = colorStatelistOf(color)
    }

    override fun updateButtonColor(inverseColor: Int) {
        chooseLabel.backgroundTintList = colorStatelistOf(inverseColor)
    }

}

@StateStrategyType(AddToEndSingleStrategy::class)
interface DictionaryEditView : MvpView {

    fun updateTitle(title: String)
    fun updateLabelTitle(labelTitle: String)
    fun updateLabelColor(color: Int)
    fun updateButtonColor(inverseColor: Int)

}

@InjectViewState
class DictionaryEditPresenter(val dictionaryDao: DictionaryDao,
                              val router: Router,
                              val dictId: Long)
    : MvpPresenter<DictionaryEditView>() {

    private lateinit var dict: Dictionary

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDict(dictId)
    }

    override fun onDestroy() {
        updateOrDeleteDict(dict)
        super.onDestroy()
    }

    fun loadDict(dictId: Long) {
        presenterScope.launch(Dispatchers.IO) {
            dict = dictionaryDao.getDictionaryItem(dictId)
            withContext(Dispatchers.Main) {
                viewState.updateLabelTitle(dict.title)
            }

        }
    }

    private fun updateOrDeleteDict(dict: Dictionary) {
        CoroutineScope(Job() + Dispatchers.Main).launch() {
            withContext(Dispatchers.IO) {
                Timber.d("Delete if empty")
                if (dict.title.isEmpty())
                    dictionaryDao.delete(dict)
                else
                    dictionaryDao.update(dict)
            }
        }
    }

    fun updateTitle(title: String) {
        dict = dict.copy(title = title)
    }


    val rand = Random(10)
    fun generateLabelColor() {
        val color = (0xff shl 24) or rand.nextInt(0, 0xffffff)
        val inverseColor = color xor 0xffffff
        Timber.d("$TAG color: ${color.toHexString()}, inverse color: ${inverseColor.toHexString()}")
        viewState.updateLabelColor(color)
        viewState.updateButtonColor(inverseColor)
    }


}