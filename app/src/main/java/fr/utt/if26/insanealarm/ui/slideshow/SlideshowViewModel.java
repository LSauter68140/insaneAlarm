package fr.utt.if26.insanealarm.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("https://cloud.jaimelaqui.ch/s/s6i8NKXdDnMYfcH");
    }

    public LiveData<String> getText() {
        return mText;
    }
}