package com.jonikoone.dictionaryforlearning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class TestJava extends MvpAppCompatFragment implements TestJavaView {

    @InjectPresenter
    TestJavaPresenter presenter;

    TextView testTextView;
    Button startButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        testTextView = view.findViewById(R.id.test_text);
        startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startStopTask();
            }
        });
    }

    @Override
    public void setText(String text) {
        testTextView.setText(text);
    }
}
