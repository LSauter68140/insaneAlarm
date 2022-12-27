package fr.utt.if26.insanealarm.ui.addAlarm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.utt.if26.insanealarm.model.Alarm;

public class AddAlarmViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private Alarm newAlarm;

    public AddAlarmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}