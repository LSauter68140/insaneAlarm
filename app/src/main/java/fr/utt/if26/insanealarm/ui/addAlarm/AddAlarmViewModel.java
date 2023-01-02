package fr.utt.if26.insanealarm.ui.addAlarm;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    //controls mutable
    private final MutableLiveData<Boolean> snoozeCtrOnscreen;
    private final MutableLiveData<Boolean> snoozeCtrVolume;
    private final MutableLiveData<Boolean> snoozeCtrPower;
    private final MutableLiveData<Boolean> snoozeCtrShake;
    private final MutableLiveData<Boolean> dismissCtrOnscreen;
    private final MutableLiveData<Boolean> dismissCtrVolume;
    private final MutableLiveData<Boolean> dismissCtrPower;
    private final MutableLiveData<Boolean> dismissCtrShake;

    //task
    // snooze
    private final MutableLiveData<String> typeSnoozeTask; // maths or text
    private final MutableLiveData<Integer> difficultySnoozeTask;
    private final MutableLiveData<Integer> numberTaskSnoozeTask;
    private final MutableLiveData<Integer> timerSnoozeTask;
    private final MutableLiveData<Boolean> canSkipSnoozeTask;
    private final MutableLiveData<Boolean> muteSoundSnoozeTask;
    // dismiss
    private final MutableLiveData<String> typeDismissTask; // maths or text
    private final MutableLiveData<Integer> difficultyDismissTask;
    private final MutableLiveData<Integer> numberTaskDismissTask;
    private final MutableLiveData<Integer> timerDismissTask;
    private final MutableLiveData<Boolean> canSkipDismissTask;
    private final MutableLiveData<Boolean> muteSoundDismissTask;

    private final Alarm newAlarm;

    public AddAlarmViewModel() {
        newAlarm = new Alarm(
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
                        new Task("Maths", 1, 2, 60, true, false)
                ),
                new Dismiss(
                        new Control(true, 0, false, false, false),
                        new Task("none", 0, 0, 0, false, true),
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

    public String getMathsExample(int difficulty) {
        // random example ?
        switch (difficulty) {
            case 1:
                return "4+7";
            case 2:
                return "3+8*4";
            case 3:
                return "54*29+56";
            default:
                return "";
        }
    }

    public String getWriteExample(int difficulty, Resources resources) {
        // random example ?
        switch (difficulty) {
            case 1:
                return resources.getString(R.string.taskWriteEasy);
            case 2:
                return resources.getString(R.string.taskWriteMedium);
            case 3:
                return resources.getString(R.string.taskWriteHard);
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

    public boolean atLeatOneDismissCrl() {
        return !Boolean.TRUE.equals(dismissCtrShake.getValue()) &&
                !Boolean.TRUE.equals(dismissCtrOnscreen.getValue()) &&
                !Boolean.TRUE.equals(dismissCtrVolume.getValue()) &&
                !Boolean.TRUE.equals(dismissCtrPower.getValue());
    }

    public Alarm getFinalAlarm() {


       /* private final MutableLiveData<Boolean> increaseVolume;
        private final MutableLiveData<Integer> alarmVolume;
        private final MutableLiveData<Integer> snoozeLimit;
        private final MutableLiveData<Integer> snoozeTime;
        private final MutableLiveData<Boolean> activateSnooze;
        private final MutableLiveData<Boolean> autoDismiss;
        private final MutableLiveData<Integer> maxTimeSecAutoDismiss;*/

        newAlarm.setName(Objects.requireNonNull(alarmLabel.getValue()));
        newAlarm.getSound().setRingtonePath(ringtoneName.getValue());
        newAlarm.getSound().setNeedVibration(needVibration.getValue());
        newAlarm.getSound().setSameAsPhone(sameAsPhone.getValue());
        newAlarm.getSound().setFlashLight(flashLightMode.getValue());

        return newAlarm;
    }


}