package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.databasemodule.database.dao.DictionaryDao
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.main.MainState
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.BaseMvpFragment
import com.jonikoone.dictionaryforlearning.util.BasePresenter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
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

class LibraryFragment : BaseMvpFragment(), LibraryView, FragmentActionContainer {

    val router: Router by inject()
    val dictionaryDao: DictionaryDao by inject()

    @InjectPresenter
    lateinit var presenter: LibraryPresenter

    @ProvidePresenter
    fun providePresenter() = LibraryPresenter(dictionaryDao, router)


    lateinit var recyclerViewListDictionaries: RecyclerView
    lateinit var constraintLayoutEmptyList: ConstraintLayout

    val adapter: LibraryAdapter by lazy {
        LibraryAdapter(presenter, router)
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
        recyclerViewListDictionaries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewListDictionaries.adapter = adapter
        constraintLayoutEmptyList = view.findViewById(R.id.library_relative_noDataLayout)
    }

    override fun updateList(dictionaries: List<Dictionary>) {
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

class LibraryAdapter(private val presenter: LibraryPresenter, val router: Router)
    : BaseAdapter<Dictionary>(), KoinComponent {
    override fun createDiffCallback(oldList: List<Dictionary>, newList: List<Dictionary>): DiffCallback<Dictionary> {
        return object : DiffCallback<Dictionary>(oldList, newList) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] === newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Dictionary> {
        return DictionaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false))
    }

    inner class DictionaryViewHolder(itemView: View) : BaseViewHolder<Dictionary>(itemView){
        override fun bind(data: Dictionary) {
            itemView.findViewById<TextView>(R.id.itemDictionary_textView_title).text = data.title
            itemView.findViewById<LinearLayout>(R.id.itemDictionary_linear_clickItem).apply {
                setOnClickListener {
                    router.navigateTo(Screens.WordsListScreen(data.id))
                }
                setOnLongClickListener {
                    router.navigateTo(Screens.DictionaryEditScreen(data.id))
                    true
                }
            }
        }
    }
}

@StateStrategyType(AddToEndSingleStrategy::class)
interface LibraryView : MvpView {
    fun updateList(dictionaries: List<Dictionary>)
    fun updateStatusList(sizeList: Int)
}

@InjectViewState
class LibraryPresenter(val dictionaryDao: DictionaryDao, val router: Router)
    : BasePresenter<LibraryView>() {

    private val listDictionaries: LiveData<List<Dictionary>> = MutableLiveData<List<Dictionary>>().apply {
        observeForever {
            viewState.updateList(it)
            viewState.updateStatusList(it.size)
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