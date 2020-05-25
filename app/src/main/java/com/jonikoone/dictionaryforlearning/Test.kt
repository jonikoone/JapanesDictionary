package com.jonikoone.dictionaryforlearning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.*
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import timber.log.Timber

const val TAG = "TestMoxy:"

/*abstract class BaseMvpAppCompatFragment<Presenter>() : MvpAppCompatFragment() {

    fun providePresenter(): Presenter {

    }

}*/

class TestMoxyFragment() : MvpAppCompatFragment(), TestView {

    var arg: Any? = null

    companion object {
        val TEST_ARG = "${TestMoxyFragment::class.qualifiedName}::arg"
        fun getInstance(obj: Any?) = TestMoxyFragment().apply {
            arguments = bundleOf(TEST_ARG to obj)
        }
    }

    /*@ProvidePresenter
    fun providePresenter(): TestPresenter {
        arg = arguments?.get(TEST_ARG) as Any
        val tag = TEST_ARG + arg.toString()
        var p = MvpFacade.getInstance().presenterStore.get(tag)
        if (p == null)
            p = TestPresenter().also {
                MvpFacade.getInstance().presenterStore.add(tag, it)
            }
        return p as TestPresenter
    }*/

    @InjectPresenter
    lateinit var presenter: TestPresenter


    lateinit var testTextView: TextView
    lateinit var startButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        testTextView = view.findViewById(R.id.test_text)
        startButton = view.findViewById(R.id.start_button)
        startButton.setOnClickListener {
            presenter.action()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("$TAG been rotate $this")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("$TAG detach $this")
    }

    override fun setText(text: String) {
        testTextView.text = text
    }

}

@StateStrategyType(AddToEndSingleStrategy::class)
interface TestView : MvpView {

    fun setText(text: String)

}

@InjectViewState
class TestPresenter constructor() : MvpPresenter<TestView>() {

    var job: Job? = null

    var number: Int = 0

    init {
        Timber.d("$TAG this presenter is: $this, and state $viewState")
    }

    fun setjob() = presenterScope.launch {
        number = 0
        while (true) {
            delay(1000)
            Timber.d("tick: $number")
            viewState.setText("time: $number")
            number++
        }
    }


    fun action() {

        if (job == null) {
            job = setjob()
            job?.start()
        } else {
            job?.cancel()
            job = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$TAG onDestroy in presenter")
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Timber.d("$TAG onFirstAttach in presenter")
    }

}