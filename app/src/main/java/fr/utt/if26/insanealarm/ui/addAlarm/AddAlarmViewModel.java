package fr.utt.if26.insanealarm.ui.addAlarm;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import fr.utt.if26.insanealarm.R;
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
    private final MutableLiveData<Integer> flashLightMode;
    private final MutableLiveData<Boolean> needVibration;
    private final MutableLiveData<Boolean> sameAsPhone;
    private final MutableLiveData<Boolean> increaseVolume;
    private final MutableLiveData<Integer> alarmVolume;
    private final MutableLiveData<Integer> snoozeLimit;
    private final MutableLiveData<Integer> snoozeTime;
    private final MutableLiveData<Boolean> activateSnooze;
    private final MutableLiveData<Boolean> autoDismiss;
    private final MutableLiveData<Integer> maxTimeSecAutoDismiss;

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
                new Snooze(
                        true,
                        10,
                        new Control(false, 0, false, false, false),
                        5,
                        2,
                        new Task("Maths", 1, 2, 60, true)
                ),
                new Dismiss(
                        new Control(true, 0, false, false, false),
                        new Task("none", 0, 0, 0, false),
                        true, 85
                )
        );
        mNewAlarm = new MutableLiveData<>();
        mNewAlarm.setValue(newAlarm);
        // first add page
        ringtoneName = new MutableLiveData<>();
        ringtoneName.setValue(newAlarm.getSound().getRingtonePath());
        flashLightMode = new MutableLiveData<>();
        flashLightMode.setValue(newAlarm.getSound().getFlashLight());
        alarmLabel = new MutableLiveData<>();
        alarmLabel.setValue(newAlarm.getLabel());
        // sound
        needVibration = new MutableLiveData<>();
        needVibration.setValue(newAlarm.getSound().getNeedVibration());
        sameAsPhone = new MutableLiveData<>();
        sameAsPhone.setValue(newAlarm.getSound().getSameAsPhone());
        increaseVolume = new MutableLiveData<>();
        increaseVolume.setValue(newAlarm.getSound().getIncreaseVolume());
        alarmVolume = new MutableLiveData<>();
        alarmVolume.setValue(newAlarm.getSound().getAlarmVolume());
        //snooze
        snoozeLimit = new MutableLiveData<>();
        snoozeLimit.setValue(newAlarm.getSnooze().getSnoozeLimit());
        snoozeTime = new MutableLiveData<>();
        snoozeTime.setValue(newAlarm.getSnooze().getSnoozeSecTime());
        activateSnooze = new MutableLiveData<>();
        activateSnooze.setValue(newAlarm.getSnooze().getActivated());
        //dismiss
        autoDismiss = new MutableLiveData<>();
        autoDismiss.setValue(newAlarm.getDismiss().getAutoDismiss());
        maxTimeSecAutoDismiss = new MutableLiveData<>();
        maxTimeSecAutoDismiss.setValue(newAlarm.getDismiss().getMaxTimeSecAutoDismiss());
    }

    public MutableLiveData<Alarm> getAlarm() {
        return mNewAlarm;
    }


    public MutableLiveData<String> getAlarmLabel() {
        return alarmLabel;
    }

    public MutableLiveData<Integer> getFlashLightMode() {
        return flashLightMode;
    }

    public MutableLiveData<Boolean> getNeedVibration() {
        return needVibration;
    }

    public MutableLiveData<String> getRingtoneName() {
        return ringtoneName;
    }

    public MutableLiveData<Boolean> getSameAsPhone() {
        return sameAsPhone;
    }

    public MutableLiveData<Boolean> getIncreaseVolume() {
        return increaseVolume;
    }

    public MutableLiveData<Integer> getAlarmVolume() {
        return alarmVolume;
    }

    public MutableLiveData<Integer> getSnoozeLimit() {
        return snoozeLimit;
    }

    public MutableLiveData<Integer> getSnoozeTime() {
        return snoozeTime;
    }

    public MutableLiveData<Boolean> getActivateSnooze() {
        return activateSnooze;
    }

    public MutableLiveData<Boolean> getAutoDismiss() {
        return autoDismiss;
    }

    public MutableLiveData<Integer> getMaxTimeSecAutoDismiss() {
        return maxTimeSecAutoDismiss;
    }

    public String ConvertFlashLightModeToStr(Integer flashLightMode, Resources resources) {
        switch (flashLightMode) {
            case 1:
                return resources.getString(R.string.alarmSoundFlashStay);
            case 2:
                return resources.getString(R.string.alarmSoundFlashByFlash);
            // same as 0
            default:
                return resources.getString(R.string.alarmSoundFlashNone);
        }
    }

}