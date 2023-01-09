package fr.utt.if26.insanealarm.ui.addAlarm;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

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

    private MutableLiveData<Alarm> mNewAlarm;
    private Alarm newAlarm;

    private MutableLiveData<String> ringtoneName;
    private MutableLiveData<String> alarmLabel;
    private MutableLiveData<String> alarmName;

    // sound
    private MutableLiveData<Integer> flashLightMode;
    private MutableLiveData<Boolean> needVibration;
    private MutableLiveData<Boolean> sameAsPhone;
    private MutableLiveData<Boolean> increaseVolume;
    private MutableLiveData<Integer> alarmVolume;

    // snooze
    private MutableLiveData<Integer> snoozeLimit;
    private MutableLiveData<Integer> snoozeTime;
    private MutableLiveData<Boolean> activateSnooze;
    private MutableLiveData<Boolean> autoDismiss;
    private MutableLiveData<Integer> maxTimeSecAutoDismiss;

    //controls mutable
    private MutableLiveData<Boolean> snoozeCtrOnscreen;
    private MutableLiveData<Boolean> snoozeCtrVolume;
    private MutableLiveData<Boolean> snoozeCtrPower;
    private MutableLiveData<Boolean> snoozeCtrShake;
    private MutableLiveData<Boolean> dismissCtrOnscreen;
    private MutableLiveData<Boolean> dismissCtrVolume;
    private MutableLiveData<Boolean> dismissCtrPower;
    private MutableLiveData<Boolean> dismissCtrShake;

    //task
    // snooze
    private MutableLiveData<String> typeSnoozeTask; // maths or text
    private MutableLiveData<Integer> difficultySnoozeTask;
    private MutableLiveData<Integer> numberTaskSnoozeTask;
    private MutableLiveData<Integer> timerSnoozeTask;
    private MutableLiveData<Boolean> canSkipSnoozeTask;
    private MutableLiveData<Boolean> muteSoundSnoozeTask;
    // dismiss
    private MutableLiveData<String> typeDismissTask; // maths or text
    private MutableLiveData<Integer> difficultyDismissTask;
    private MutableLiveData<Integer> numberTaskDismissTask;
    private MutableLiveData<Integer> timerDismissTask;
    private MutableLiveData<Boolean> canSkipDismissTask;
    private MutableLiveData<Boolean> muteSoundDismissTask;

    //wakeUp check
    private MutableLiveData<Boolean> wkupChkIsActivate;
    private MutableLiveData<Integer> wkupChkDelayAfterDismiss;
    private MutableLiveData<Integer> wkupChkCountdownTime;


    // alarmFrequency
    private MutableLiveData<LocalDateTime> nextRing;
    private MutableLiveData<ArrayList<Boolean>> alarmFrequencyDay;

    public AddAlarmViewModel() {
        newAlarm = new Alarm(
                "Mon alarme",
                LocalTime.now(),
                true,
                "Oouh c'est l'heure de se lever",
                new AlarmFrequency(
                        LocalDateTime.now(),
                        true,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false),
                new Sound("/ringtone.mp3", false, 0, true, true, 0),
                new WakeupCheck(false, 0, 0),
                new Snooze(
                        true,
                        10,
                        new Control(false, 0, false, false, false),
                        5,
                        2,
                        new Task("Maths", 1, 2, 60, true, false)
                ),
                new Dismiss(
                        new Control(true, 0, false, false, false),
                        new Task("none", 0, 0, 0, false, true),
                        true, 85
                )
        );
        initMutableLiveData();
    }

    private void initMutableLiveData() {
        mNewAlarm = new MutableLiveData<>();
        mNewAlarm.setValue(newAlarm);
        alarmName = new MutableLiveData<>();
        alarmName.setValue(newAlarm.getName());
        // first add page
        ringtoneName = new MutableLiveData<>();
        ringtoneName.setValue(newAlarm.getSound().getRingtonePath());
        flashLightMode = new MutableLiveData<>();
        flashLightMode.setValue(newAlarm.getSound().getFlashLight());
        alarmLabel = new MutableLiveData<>();
        alarmLabel.setValue(newAlarm.getLabel(alarmLabel.getValue()));

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

        // controls
        snoozeCtrOnscreen = new MutableLiveData<>();
        snoozeCtrOnscreen.setValue(newAlarm.getSnooze().getControl().isButtonOnScreen());
        snoozeCtrVolume = new MutableLiveData<>();
        snoozeCtrVolume.setValue(newAlarm.getSnooze().getControl().isVolumeButton());
        snoozeCtrShake = new MutableLiveData<>();
        snoozeCtrShake.setValue(newAlarm.getSnooze().getControl().isShakeUrPhone());
        snoozeCtrPower = new MutableLiveData<>();
        snoozeCtrPower.setValue(newAlarm.getSnooze().getControl().isPowerButton());

        dismissCtrOnscreen = new MutableLiveData<>();
        dismissCtrOnscreen.setValue(newAlarm.getDismiss().getControl().isButtonOnScreen());
        dismissCtrVolume = new MutableLiveData<>();
        dismissCtrVolume.setValue(newAlarm.getDismiss().getControl().isVolumeButton());
        dismissCtrShake = new MutableLiveData<>();
        dismissCtrShake.setValue(newAlarm.getDismiss().getControl().isShakeUrPhone());
        dismissCtrPower = new MutableLiveData<>();
        dismissCtrPower.setValue(newAlarm.getDismiss().getControl().isPowerButton());

        //task
        typeSnoozeTask = new MutableLiveData<>();
        typeSnoozeTask.setValue(newAlarm.getSnooze().getTask().getType());
        difficultySnoozeTask = new MutableLiveData<>();
        difficultySnoozeTask.setValue(newAlarm.getSnooze().getTask().getDifficulty());
        numberTaskSnoozeTask = new MutableLiveData<>();
        numberTaskSnoozeTask.setValue(newAlarm.getSnooze().getTask().getNumberTask());
        timerSnoozeTask = new MutableLiveData<>();
        timerSnoozeTask.setValue(newAlarm.getSnooze().getTask().getTimer());
        canSkipSnoozeTask = new MutableLiveData<>();
        canSkipSnoozeTask.setValue(newAlarm.getSnooze().getTask().isCanSkip());
        muteSoundSnoozeTask = new MutableLiveData<>();
        muteSoundSnoozeTask.setValue(newAlarm.getSnooze().getTask().isMuteSound());

        typeDismissTask = new MutableLiveData<>();
        typeDismissTask.setValue(newAlarm.getDismiss().getTask().getType());
        difficultyDismissTask = new MutableLiveData<>();
        difficultyDismissTask.setValue(newAlarm.getDismiss().getTask().getDifficulty());
        numberTaskDismissTask = new MutableLiveData<>();
        numberTaskDismissTask.setValue(newAlarm.getDismiss().getTask().getNumberTask());
        timerDismissTask = new MutableLiveData<>();
        timerDismissTask.setValue(newAlarm.getDismiss().getTask().getTimer());
        canSkipDismissTask = new MutableLiveData<>();
        canSkipDismissTask.setValue(newAlarm.getDismiss().getTask().isCanSkip());
        muteSoundDismissTask = new MutableLiveData<>();
        muteSoundDismissTask.setValue(newAlarm.getDismiss().getTask().isMuteSound());

        // wakeup check

        wkupChkIsActivate = new MutableLiveData<>();
        wkupChkIsActivate.setValue(newAlarm.getWakeupCheck().getActivate());
        wkupChkDelayAfterDismiss = new MutableLiveData<>();
        wkupChkDelayAfterDismiss.setValue(newAlarm.getWakeupCheck().getDelayAfterDismiss());
        wkupChkCountdownTime = new MutableLiveData<>();
        wkupChkCountdownTime.setValue(newAlarm.getWakeupCheck().getCountdownTime());

        // frequency
        alarmFrequencyDay = new MutableLiveData<>();
        alarmFrequencyDay.setValue(newAlarm.getAlarmFrequency().getAllWeek());
        nextRing = new MutableLiveData<>();
        nextRing.setValue(newAlarm.getAlarmFrequency().getNextRing());

    }

    public void addExistingAlarm(Alarm alarm) {
        newAlarm = alarm;
        initMutableLiveData();
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

    public MutableLiveData<Boolean> getSnoozeCtrOnscreen() {
        return snoozeCtrOnscreen;
    }

    public MutableLiveData<Boolean> getSnoozeCtrVolume() {
        return snoozeCtrVolume;
    }

    public MutableLiveData<Boolean> getSnoozeCtrPower() {
        return snoozeCtrPower;
    }

    public MutableLiveData<Boolean> getSnoozeCtrShake() {
        return snoozeCtrShake;
    }

    public MutableLiveData<Boolean> getDismissCtrOnscreen() {
        return dismissCtrOnscreen;
    }

    public MutableLiveData<Boolean> getDismissCtrVolume() {
        return dismissCtrVolume;
    }

    public MutableLiveData<Boolean> getDismissCtrPower() {
        return dismissCtrPower;
    }

    public MutableLiveData<Boolean> getDismissCtrShake() {
        return dismissCtrShake;
    }

    public MutableLiveData<String> getTypeSnoozeTask() {
        return typeSnoozeTask;
    }

    public MutableLiveData<Integer> getDifficultySnoozeTask() {
        return difficultySnoozeTask;
    }

    public MutableLiveData<Integer> getNumberTaskSnoozeTask() {
        return numberTaskSnoozeTask;
    }

    public MutableLiveData<Integer> getTimerSnoozeTask() {
        return timerSnoozeTask;
    }

    public MutableLiveData<Boolean> getCanSkipSnoozeTask() {
        return canSkipSnoozeTask;
    }

    public MutableLiveData<Boolean> getMuteSoundSnoozeTask() {
        return muteSoundSnoozeTask;
    }

    public MutableLiveData<String> getTypeDismissTask() {
        return typeDismissTask;
    }

    public MutableLiveData<Integer> getDifficultyDismissTask() {
        return difficultyDismissTask;
    }

    public MutableLiveData<Integer> getNumberTaskDismissTask() {
        return numberTaskDismissTask;
    }

    public MutableLiveData<Integer> getTimerDismissTask() {
        return timerDismissTask;
    }

    public MutableLiveData<Boolean> getCanSkipDismissTask() {
        return canSkipDismissTask;
    }

    public MutableLiveData<Boolean> getMuteSoundDismissTask() {
        return muteSoundDismissTask;
    }

    public MutableLiveData<String> getAlarmName() {
        return alarmName;
    }

    public MutableLiveData<Boolean> getWkupChkIsActivate() {
        return wkupChkIsActivate;
    }

    public MutableLiveData<Integer> getWkupChkDelayAfterDismiss() {
        return wkupChkDelayAfterDismiss;
    }

    public MutableLiveData<Integer> getWkupChkCountdownTime() {
        return wkupChkCountdownTime;
    }

    public MutableLiveData<LocalDateTime> getNextRing() {
        return nextRing;
    }

    public MutableLiveData<ArrayList<Boolean>> getAlarmFrequencyDay() {
        return alarmFrequencyDay;
    }

    public int getTypeToInt(String type) {
        if (type == null)
            return 0;
        switch (type) {
            case "Maths":
                return 1;
            case "Write":
                return 2;
            default:
                return 0;
        }
    }

    public String getDifficultyToString(int difficulty, Resources resources) {
        switch (difficulty) {
            case 1:
                return resources.getString(R.string.taskDifficultyEasy);
            case 2:
                return resources.getString(R.string.taskDifficultyMedium);
            case 3:
                return resources.getString(R.string.taskDifficultyHard);
            default:
                return "";
        }
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

    public boolean atLeastOneDismissCrl() {
        return !Boolean.TRUE.equals(dismissCtrShake.getValue()) &&
                !Boolean.TRUE.equals(dismissCtrOnscreen.getValue()) &&
                !Boolean.TRUE.equals(dismissCtrVolume.getValue()) &&
                !Boolean.TRUE.equals(dismissCtrPower.getValue());
    }


    public Alarm getFinalAlarm() {


        // alarm
        newAlarm.setName(Objects.requireNonNull(alarmName.getValue()));
        newAlarm.setLabel(Objects.requireNonNull(alarmLabel.getValue()));
        newAlarm.setTimeToGoOff(Objects.requireNonNull(nextRing.getValue()).toLocalTime());


        // sound
        newAlarm.getSound().setRingtonePath(ringtoneName.getValue());
        newAlarm.getSound().setNeedVibration(needVibration.getValue());
        newAlarm.getSound().setSameAsPhone(sameAsPhone.getValue());
        newAlarm.getSound().setFlashLight(flashLightMode.getValue());
        newAlarm.getSound().setAlarmVolume(alarmVolume.getValue());
        newAlarm.getSound().setIncreaseVolume(increaseVolume.getValue());
        // snooze
        newAlarm.getSnooze().setActivated(activateSnooze.getValue());
        newAlarm.getSnooze().setSnoozeLimit(snoozeLimit.getValue());
        newAlarm.getSnooze().setSnoozeSecTime(maxTimeSecAutoDismiss.getValue());
        newAlarm.getSnooze().getControl().setButtonOnScreen(Boolean.TRUE.equals(snoozeCtrOnscreen.getValue()));
        newAlarm.getSnooze().getControl().setPowerButton(Boolean.TRUE.equals(snoozeCtrPower.getValue()));
        newAlarm.getSnooze().getControl().setVolumeButton(Boolean.TRUE.equals(snoozeCtrVolume.getValue()));
        newAlarm.getSnooze().getControl().setShakeUrPhone(Boolean.TRUE.equals(snoozeCtrShake.getValue()));
        newAlarm.getSnooze().getTask().setCanSkip(Boolean.TRUE.equals(canSkipSnoozeTask.getValue()));
        newAlarm.getSnooze().getTask().setDifficulty(difficultySnoozeTask.getValue());
        newAlarm.getSnooze().getTask().setNumberTask(numberTaskSnoozeTask.getValue());
        newAlarm.getSnooze().getTask().setMuteSound(Boolean.TRUE.equals(muteSoundSnoozeTask.getValue()));
        newAlarm.getSnooze().getTask().setTimer(timerSnoozeTask.getValue());
        newAlarm.getSnooze().getTask().setType(typeSnoozeTask.getValue());
        // dismiss
        newAlarm.getDismiss().setAutoDismiss(autoDismiss.getValue());
        newAlarm.getDismiss().setMaxTimeSecAutoDismiss(maxTimeSecAutoDismiss.getValue());
        newAlarm.getDismiss().getControl().setButtonOnScreen(Boolean.TRUE.equals(dismissCtrOnscreen.getValue()));
        newAlarm.getDismiss().getControl().setPowerButton(Boolean.TRUE.equals(dismissCtrPower.getValue()));
        newAlarm.getDismiss().getControl().setVolumeButton(Boolean.TRUE.equals(dismissCtrVolume.getValue()));
        newAlarm.getDismiss().getControl().setShakeUrPhone(Boolean.TRUE.equals(dismissCtrShake.getValue()));
        newAlarm.getDismiss().getTask().setCanSkip(Boolean.TRUE.equals(canSkipDismissTask.getValue()));
        newAlarm.getDismiss().getTask().setDifficulty(difficultyDismissTask.getValue());
        newAlarm.getDismiss().getTask().setNumberTask(numberTaskDismissTask.getValue());
        newAlarm.getDismiss().getTask().setMuteSound(Boolean.TRUE.equals(muteSoundDismissTask.getValue()));
        newAlarm.getDismiss().getTask().setTimer(timerDismissTask.getValue());
        newAlarm.getDismiss().getTask().setType(typeDismissTask.getValue());

        // wakeUp check
        newAlarm.getWakeupCheck().setActivate(wkupChkIsActivate.getValue());
        newAlarm.getWakeupCheck().setCountdownTime(wkupChkCountdownTime.getValue());
        newAlarm.getWakeupCheck().setDelayAfterDismiss(wkupChkDelayAfterDismiss.getValue());

        // alarm frequency
        newAlarm.getFrequency().setArrayToWeek(alarmFrequencyDay.getValue());
        newAlarm.getFrequency().setNextRing(Objects.requireNonNull(nextRing.getValue()));
        return newAlarm;
    }


}