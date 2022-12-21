package fr.utt.if26.insanealarm.ui.alarm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.insanealarm.database.AlarmRepository;
import fr.utt.if26.insanealarm.model.Alarm;

public class AlarmViewModel extends AndroidViewModel {

    private final AlarmRepository alarmRepository;
    private final LiveData<List<Alarm>> allAlarms;

    public AlarmViewModel(Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
        allAlarms = alarmRepository.getAllAlarms();
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return allAlarms;
    }

    public void insert(Alarm alarm) {
        alarmRepository.insertAlarm(alarm);
    }

    public void updateAlarm(Alarm alarm) {
        alarmRepository.updateAlarm(alarm);
    }

    public void deleteAlarmById(Integer id) {
        alarmRepository.deleteAlarmById(id);
    }

    public void deleteAll() {
        alarmRepository.deleteAll();
    }
}