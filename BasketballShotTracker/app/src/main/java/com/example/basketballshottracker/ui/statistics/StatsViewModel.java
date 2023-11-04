package com.example.basketballshottracker.ui.statistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class StatsViewModel extends androidx.lifecycle.ViewModel {

    private final MutableLiveData<String> mText;

    public StatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is statistics fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}