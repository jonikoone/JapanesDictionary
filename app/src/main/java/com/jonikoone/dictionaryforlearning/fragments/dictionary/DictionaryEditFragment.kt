package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.content.Context
import android.database.DataSetObservable
import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.os.bundleOf
import com.google.android.material.textfield.TextInputEditText
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.dao.LabelDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment
import com.jonikoone.dictionaryforlearning.util.SimpleTextWatcher
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
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class DictionaryEditFragment() : BaseMvpFragment(), DictionaryEditView,
        AdapterView.OnItemSelectedListener, SimpleTextWatcher {

    companion object {
        val ARG_DICT_ID = "${this::class.qualifiedName}::dictionary_id"
        fun newInstance(dictId: Long) = DictionaryEditFragment().apply {
            arguments = bundleOf(ARG_DICT_ID to dictId)
        }
    }

    val router: Router by inject()
    val dictionaryDao: DictionaryDao by inject()
    val labelDao: LabelDao by inject()

    val dictionaryId: Long by lazy { arguments?.getLong(ARG_DICT_ID) as Long }

    @InjectPresenter
    lateinit var presenter: DictionaryEditPresenter

    @ProvidePresenter
    fun providePresenter() = DictionaryEditPresenter(dictionaryDao, labelDao, router, dictionaryId)

    lateinit var titleDict: TextInputEditText
    lateinit var chooseLabel: AppCompatSpinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dictionary_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleDict = view.findViewById(R.id.dictionaryEdit_editText_title)
        titleDict.addTextChangedListener(this)
        chooseLabel = view.findViewById(R.id.dictionaryEdit_spinner_chooseLabel)
        chooseLabel.onItemSelectedListener = this
    }

    override fun updateTitle(title: String) {
        titleDict.setText(title.toCharArray(), 0, title.length)
    }

    override fun setListToAdapter(list: List<Label>) {
        chooseLabel.adapter = context?.let {
            ListSpinnerAdapter(it, R.layout.item_label, list)
        }
    }

    override fun selectIndexLabelList(position: Int) {
        Timber.d("select item by index $position")
        chooseLabel.setSelection(position)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        presenter.updateTitle(s.toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        presenter.deleteLabel()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Timber.d("select position: $position")
        if (position == 0) {
            presenter.deleteLabel()
        } else {
            presenter.setLabel(id)
        }
    }

}

class ListSpinnerAdapter(val context: Context, @LayoutRes val layoutId: Int, list: List<Label>)
    : SpinnerAdapter, ListAdapter {
    val observable = DataSetObservable()


    val noSelectItem = -5L

    private val list = arrayOf(noSelectItem, *list.toTypedArray())

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = if (position == 0) {
            if (count != 1)
                LayoutInflater.from(parent.context).inflate(R.layout.item_label_no_select, parent, false)
            else
                LayoutInflater.from(parent.context).inflate(R.layout.item_label_empty_data, parent, false)
        } else {
            val itemList = list[position] as Label
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false).apply {
                findViewById<TextView>(R.id.title_itemLabel)?.text = itemList.title
                findViewById<ImageView>(R.id.image_itemLabel)?.apply {
                    setImageDrawable(context.getDrawable(R.drawable.ic_label))
                    imageTintList = colorStatelistOf(itemList.color)
                }
                findViewById<TextView>(R.id.diff_itemLabel)?.text = itemList.difficulty.toString()
            }
        }

        return view
    }


    override fun registerDataSetObserver(observer: DataSetObserver?) {
        observable.registerObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        observable.unregisterObserver(observer)
    }

    override fun getItemViewType(position: Int): Int = 1

    override fun getItem(position: Int): Any = list[position]

    override fun getViewTypeCount(): Int = 1

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
            getView(position, convertView, parent)


    override fun getCount(): Int = list.size
    override fun getItemId(position: Int): Long =
            if (position == 0) noSelectItem
            else (list[position] as Label).id

    override fun hasStableIds(): Boolean = true

    override fun isEnabled(position: Int): Boolean = true
    override fun areAllItemsEnabled(): Boolean = true
}

@StateStrategyType(AddToEndSingleStrategy::class)
interface DictionaryEditView : MvpView {

    fun updateTitle(title: String)
    fun setListToAdapter(list: List<Label>)
    fun selectIndexLabelList(position: Int)

}

@InjectViewState
class DictionaryEditPresenter(val dictionaryDao: DictionaryDao,
                              val labelDao: LabelDao,
                              val router: Router,
                              val dictId: Long)
    : MvpPresenter<DictionaryEditView>() {

    private lateinit var labels: List<Label>
    private lateinit var dict: Dictionary

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDict(dictId)
    }

    override fun onDestroy() {
        updateOrDeleteDict(dict)
        super.onDestroy()
    }

    private fun loadDict(dictId: Long) {
        presenterScope.launch(Dispatchers.IO) {
            dict = dictionaryDao.getDictionaryItem(dictId)
            labels = labelDao.getLabels1()
            val labelIndex: Int? = if (dict.labelId != null) {
                labels.indexOf(labels.firstOrNull { it.id == dict.labelId })
            } else null
            Timber.d("load dict: $dict, index is $labelIndex")
            withContext(Dispatchers.Main) {
                viewState.updateTitle(dict.title)
                viewState.setListToAdapter(labels)
                if (labelIndex != null)
                    viewState.selectIndexLabelList(labelIndex + 1)
            }
        }
    }

    private fun updateOrDeleteDict(dict: Dictionary) {
        CoroutineScope(Job() + Dispatchers.Main).launch() {
            withContext(Dispatchers.IO) {
                if (dict.title.isEmpty()) {
                    Timber.d("title is empty, delete blank note")
                    dictionaryDao.delete(dict)
                } else {
                    Timber.d("title is not empty, update note")
                    dictionaryDao.update(dict)
                }
            }
        }
    }

    fun updateTitle(title: String) {
        dict = dict.copy(title = title)
    }

    fun deleteLabel() {
        dict = dict.copy(labelId = null)
    }

    fun setLabel(id: Long) {
        dict = dict.copy(labelId = id)
    }

}