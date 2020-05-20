package com.jonikoone.dictionaryforlearning

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jonikoone.dictionaryforlearning.util.mvpScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class TestMoxyFragment() : MvpAppCompatFragment(), TestView {

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

    override fun setText(text: String) {
        testTextView.text = text
    }

}

@StateStrategyType(AddToEndSingleStrategy::class)
interface TestView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setText(text: String)

}

@InjectViewState
class TestPresenter : MvpPresenter<TestView>() {

    var job: Job? = null

    var number: Int = 0

    init {
        Timber.d("this presenter is: $this, and state $viewState")
    }

    fun setjob()  = mvpScope.launch {
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

}