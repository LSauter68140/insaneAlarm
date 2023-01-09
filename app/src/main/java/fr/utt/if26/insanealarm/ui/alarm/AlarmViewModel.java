package fr.utt.if26.insanealarm.ui.alarm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import fr.utt.if26.insanealarm.database.AlarmRepository;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.utils.AlarmUtils;

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

    public void checkNextGoOff(Alarm alarm) {
        // check frequency
        Boolean[] nextDayGooff = alarm.getFrequency().getAllWeek().toArray(new Boolean[0]);
        Duration totalDuration = AlarmUtils.getDurationNextGoOff(alarm.getTimeToGoOff(), nextDayGooff);
        alarm.getFrequency().setNextRing(LocalDateTime.now().plus(totalDuration));
        // update the DB
        alarmRepository.updateAlarm(alarm);
    }
}