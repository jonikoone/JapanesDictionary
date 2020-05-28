package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.dao.DictionaryWithLabelDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.databasemodule.database.entites.DictionaryWithLabel
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.main.MainState
import com.jonikoone.dictionaryforlearning.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import ru.terrakok.cicerone.Router
import timber.log.Timber

class LibraryFragment : BaseMvpFragment(), LibraryView, FragmentActionContainer {

    val router: Router by inject()
    val dictionaryDao: DictionaryDao by inject()
    val dictionaryWithLabelDao: DictionaryWithLabelDao by inject()

    @InjectPresenter
    lateinit var presenter: LibraryPresenter

    @ProvidePresenter
    fun providePresenter() = LibraryPresenter(
        dictionaryWithLabelDao = dictionaryWithLabelDao,
        dictionaryDao = dictionaryDao, router = router
    )


    lateinit var recyclerViewListDictionaries: RecyclerView
    lateinit var constraintLayoutEmptyList: ConstraintLayout

    val adapter: LibraryAdapter by lazy {
        LibraryAdapter(presenter, router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(presenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewListDictionaries = view.findViewById(R.id.recycler_dictionaryList)
        recyclerViewListDictionaries.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewListDictionaries.adapter = adapter
        constraintLayoutEmptyList = view.findViewById(R.id.library_relative_noDataLayout)
    }

    override fun updateList(dictionaries: List<DictionaryWithLabel>) {
        adapter.updateList(dictionaries)
    }

    override fun updateStatusList(sizeList: Int) {
        if (sizeList > 0)
            constraintLayoutEmptyList.visibility = View.GONE
        else
            constraintLayoutEmptyList.visibility = View.VISIBLE
    }

    override val state: MainState = MainState(
        isShowFab = true,
        iconFab = R.drawable.ic_add,
        clickOnFab = {
            presenter.addDictionary()
        }
    )

}

class LibraryAdapter(private val presenter: LibraryPresenter, val router: Router) :
    BaseAdapter<DictionaryWithLabel>(), KoinComponent {
    override fun createDiffCallback(
        oldList: List<DictionaryWithLabel>,
        newList: List<DictionaryWithLabel>
    ): DiffCallback<DictionaryWithLabel> {
        return object : DiffCallback<DictionaryWithLabel>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] === newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseViewHolder<DictionaryWithLabel> {
        Timber.d("create view holder with viewType: $viewType")
        return DictionaryItemWithLabelViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dictionary_with_label, parent, false)
        )

    }

    fun setListenersForRouting(view: View, id: Long) {
        Timber.d("listener open dict by id: $id")
        view.findViewById<View>(R.id.itemDictionary_itemClick).apply {
            setOnClickListener {
                router.navigateTo(Screens.WordsListScreen(id))
            }
            setOnLongClickListener {
                router.navigateTo(Screens.DictionaryEditScreen(id))
                true
            }
        }

    }

    inner class DictionaryItemWithLabelViewHolder(itemView: View) :
        BaseViewHolder<DictionaryWithLabel>(itemView) {
        private val dictionaryTitle = itemView.findViewById<TextView>(R.id.itemDictionary_textView_dictionaryTitle)
        private val labelContainer = itemView.findViewById<View>(R.id.itemDictionary_labelContainer)
        private val labelIcon = itemView.findViewById<ImageView>(R.id.itemDictionary_imageView_labelIcon)
        private val labelDiffiuculty = itemView.findViewById<TextView>(R.id.itemDictionary_textView_labelDifficulty)
        private val labelTitle = itemView.findViewById<TextView>(R.id.itemDictionary_textView_labelTitle)

        override fun bind(data: DictionaryWithLabel) {
            Timber.d("dictionary with label item: $data")
            dictionaryTitle.text = data.dictionary.title
            labelContainer.visibility = if (data.dictionary.labelId == null) View.GONE else View.VISIBLE
            data.label?.let { label ->
                labelIcon.imageTintList = colorStatelistOf(label.color)
                labelDiffiuculty.text = label.difficulty.toString()
                labelTitle.text = label.title
            }
            setListenersForRouting(itemView, data.dictionary.id)
        }
    }
}

@StateStrategyType(AddToEndSingleStrategy::class)
interface LibraryView : MvpView {
    fun updateList(dictionaries: List<DictionaryWithLabel>)
    fun updateStatusList(sizeList: Int)
}

@InjectViewState
class LibraryPresenter(
    val dictionaryWithLabelDao: DictionaryWithLabelDao,
    val dictionaryDao: DictionaryDao, val router: Router
) :
    BasePresenter<LibraryView>(), LifecycleObserver {

    private lateinit var listDictionaries: LiveData<List<DictionaryWithLabel>>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listDictionaries = dictionaryDao.getDictionariesWithLabelAsLive().apply {
            observeForever {
                Timber.d("live data update: list = $it")
                viewState.updateList(it)
                viewState.updateStatusList(it.size)
            }
        }
    }

    fun addDictionary() {
        presenterScope.launch(Dispatchers.IO) {
            val newDict = dictionaryDao.insertAndGet(Dictionary())
            withContext(Dispatchers.Main) {
                router.navigateTo(Screens.DictionaryEditScreen(newDict.id))
            }
        }
    }


}