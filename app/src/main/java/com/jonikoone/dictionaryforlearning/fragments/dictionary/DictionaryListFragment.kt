package com.jonikoone.dictionaryforlearning.fragments.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jonikoone.databasemodule.database.entites.Dictionary
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.Router

class DictionaryListFragment : MvpAppCompatFragment(), DictionaryListView {

    @InjectPresenter
    lateinit var presenter: DictionaryListPresenter
    val router: Router by inject()

    lateinit var recyclerViewListDictionaries: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dictionary_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewListDictionaries = view.findViewById(R.id.recycler_dictionaryList)
        recyclerViewListDictionaries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewListDictionaries.adapter = presenter.adapter
    }

    override fun bind(data: Dictionary) {
        view?.setOnClickListener {
            router.navigateTo(Screens.WordsListScreen(data))
        }
        view?.setOnLongClickListener {
            router.navigateTo(Screens.DictionaryEditScreen(data))
            true
        }
        view?.findViewById<TextView>(R.id.item_dictionary_title_text_view)?.let {
            it.text = data.title
        }
    }

}

class DictionariesAdapter(private val presenter: DictionaryListPresenter) : BaseAdapter<Dictionary>() {
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
            presenter.bind(data)
        }
    }
}

interface DictionaryListView : MvpView {
    fun bind(data: Dictionary)
}

@InjectViewState
class DictionaryListPresenter : MvpPresenter<DictionaryListView>() {
    val adapter = DictionariesAdapter(this)

    fun bind(data: Dictionary){
        viewState.bind(data)
    }
}