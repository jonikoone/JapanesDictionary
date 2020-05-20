package com.jonikoone.dictionaryforlearning.fragments.labels

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jonikoone.databasemodule.database.entites.Label
import com.jonikoone.dictionaryforlearning.R
import com.jonikoone.dictionaryforlearning.navigation.FragmentActionContainer
import com.jonikoone.dictionaryforlearning.navigation.Screens
import com.jonikoone.dictionaryforlearning.presentation.label.LabelListPresenter
import com.jonikoone.dictionaryforlearning.presentation.label.LabelListView
import com.jonikoone.dictionaryforlearning.presentation.main.MainAction
import com.jonikoone.dictionaryforlearning.presentation.main.MainPresenter
import com.jonikoone.dictionaryforlearning.presentation.main.MainView
import com.jonikoone.dictionaryforlearning.util.BaseAdapter
import com.jonikoone.dictionaryforlearning.util.DiffCallback
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

class LabelListFragment : MvpAppCompatFragment(), LabelListView, FragmentActionContainer {

    @InjectPresenter
    lateinit var presenter: LabelListPresenter

    val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_labels_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.recycler_labelList)?.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.adapter = presenter.adapter
        }
        presenter.loadDataToAdapter()
    }

    override fun clickOnAddLabel() {
        presenter.addLabel()
    }

    override fun clickOnLabel(label: Label) {
        router.navigateTo(Screens.LabelScreen(label))
        //navigationManager?.topNavigate(Screens.LabelScreen(label))
    }

    override fun updateList(labelList: List<Label>) {
        presenter.updateList(labelList)
    }

    override val action = MainAction(
            iconFab = R.drawable.ic_add,
            clickOnFab = {
                clickOnAddLabel()
            }
    )

}

class LabelListAdapter(private val presenter: LabelListPresenter) : BaseAdapter<Label>(), KoinComponent {
    val router: Router by inject()

    override fun createDiffCallback(
            oldList: List<Label>,
            newList: List<Label>
    ): DiffCallback<Label> =
            object :
                    DiffCallback<Label>(oldList, newList) {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        oldList[oldItemPosition].id == newList[newItemPosition].id

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        oldList[oldItemPosition].title == newList[newItemPosition].title ||
                                oldList[oldItemPosition].difficulty == newList[newItemPosition].difficulty ||
                                oldList[oldItemPosition].color == newList[newItemPosition].color
            }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BaseViewHolder<Label> =
            LabelViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false)
            )

    inner class LabelViewHolder(private val view: View) :
            BaseViewHolder<Label>(view),
            KoinComponent {
        override fun bind(data: Label) {
            view.findViewById<ImageView>(R.id.image_itemLabel)?.imageTintList = ColorStateList.valueOf(data.color)
            view.findViewById<TextView>(R.id.diff_itemLabel)?.text = data.difficulty.toString()
            view.findViewById<TextView>(R.id.title_itemLabel)?.text = data.title
            view.setOnClickListener {
                router.navigateTo(Screens.LabelScreen(data))
            }
        }

    }

}

