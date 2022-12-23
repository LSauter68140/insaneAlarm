package fr.utt.if26.insanealarm.ui.addEdit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddEditViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddEditViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}