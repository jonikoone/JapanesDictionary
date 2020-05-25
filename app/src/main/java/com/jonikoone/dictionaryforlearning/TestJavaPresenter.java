package com.jonikoone.dictionaryforlearning;

import android.os.AsyncTask;
import android.util.Log;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class TestJavaPresenter extends MvpPresenter<TestJavaView> {

    AsyncTask<Void, Integer, Void> asyncTask;

    public void startStopTask() {
        if (asyncTask == null) {
            asyncTask = new AT();
            asyncTask.execute();
        } else {
            asyncTask.cancel(true);
            asyncTask = null;
        }
    }

    class AT extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            Integer number = 0;

            while (!isCancelled()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(number++);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Integer number = values[0];
            Log.d("Tag", "Test: " + number.toString());
            getViewState().setText(number.toString());
        }


    }

}
