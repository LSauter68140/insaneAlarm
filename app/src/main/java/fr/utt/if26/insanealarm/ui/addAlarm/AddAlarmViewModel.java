package fr.utt.if26.insanealarm.ui.addAlarm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.model.AlarmFrequency;
import fr.utt.if26.insanealarm.model.Control;
import fr.utt.if26.insanealarm.model.Dismiss;
import fr.utt.if26.insanealarm.model.Snooze;
import fr.utt.if26.insanealarm.model.Sound;
import fr.utt.if26.insanealarm.model.Task;
import fr.utt.if26.insanealarm.model.WakeupCheck;

public class AddAlarmViewModel extends ViewModel {

    private final MutableLiveData<Alarm> mNewAlarm;
    private final MutableLiveData<String> ringtoneName;
    private final MutableLiveData<String> alarmLabel;

    public AddAlarmViewModel() {
        Alarm newAlarm = new Alarm(
                "Mon alarme",
                LocalTime.now(),
                true,
                "Oouh c'est l'heure de se lever",
                new AlarmFrequency(
                        LocalDateTime.now(),
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false),
                new Sound("/ringtone.mp3", false, 0, true, true, 0),
                new WakeupCheck(false, 0, 0),
                new Snooze(10,
                        new Control(false, 0, false, false, false),
                        60,
                        2,
                        new Task("Maths", 1, 2, 60, true)
                ),
                new Dismiss(
                        new Control(true, 0, false, false, false),
                        new Task("none", 0, 0, 0, false),
                        true
                )
        );
        mNewAlarm = new MutableLiveData<>();
        mNewAlarm.setValue(newAlarm);
        ringtoneName = new MutableLiveData<>();
        ringtoneName.setValue(newAlarm.getSound().getRingtonePath());

        alarmLabel = new MutableLiveData<>();
        alarmLabel.setValue(newAlarm.getLabel());
    }

    public MutableLiveData<Alarm> getAlarm() {
        return mNewAlarm;
    }

    public MutableLiveData<String> getRingtone() {
        return ringtoneName;
    }

    public MutableLiveData<String> getAlarmLabel() {
        return alarmLabel;
    }
}