package com.jonikoone.dictionaryforlearning;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TestJavaView extends MvpView {
    void setText(String text);
}
