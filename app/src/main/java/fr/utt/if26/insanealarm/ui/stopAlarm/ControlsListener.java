package fr.utt.if26.insanealarm.ui.stopAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import fr.utt.if26.insanealarm.broadcastReceiver.PowerOnOffButtonReceiver;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.service.RingtonePlayingService;
import fr.utt.if26.insanealarm.service.VolumeButtonObserver;
import fr.utt.if26.insanealarm.ui.alarm.AlarmViewModel;
import fr.utt.if26.insanealarm.utils.AlarmUtils;
import fr.utt.if26.insanealarm.worker.AlarmGoOffWorker;

public class ControlsListener extends AppCompatActivity {
    // add listener for each button and wait
    //broadcaster
    PowerOnOffButtonReceiver powerOnOffButtonReceveier;

    public MutableLiveData<Boolean> mediaIsActivate;
    public String type;
    public Alarm alarm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmViewModel addAlarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

        // retrieve info from dismiss/ snooze activity

        Intent getIntent = getIntent();
        type = getIntent.getStringExtra("type");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            alarm = getIntent.getSerializableExtra("alarm", Alarm.class);
        } else {
            alarm = (Alarm) getIntent.getSerializableExtra("alarm");
        }


        mediaIsActivate = new MutableLiveData<>();
        mediaIsActivate.setValue(true);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        boolean activatePowerBtn = Objects.equals(type, "snooze") ?
                alarm.getSnooze().getControl().isPowerButton() :
                alarm.getDismiss().getControl().isPowerButton();

        //on off button
        if (activatePowerBtn) {
            powerOnOffButtonReceveier = new PowerOnOffButtonReceiver();
            registerReceiver(powerOnOffButtonReceveier, intentFilter);
            IntentFilter filter = new IntentFilter();
            filter.addAction("service.on.off.btn");
            BroadcastReceiver updateUIReciver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mediaIsActivate.setValue(false);
                    unregisterReceiver(powerOnOffButtonReceveier);
                }
            };
            registerReceiver(updateUIReciver, filter);
        }

        boolean activateVolumeBtn = Objects.equals(type, "snooze") ?
                alarm.getSnooze().getControl().isVolumeButton() :
                alarm.getDismiss().getControl().isVolumeButton();
        // volume button
        if (activateVolumeBtn) {
            getApplicationContext().getContentResolver()
                    .registerContentObserver(
                            android.provider.Settings.System.CONTENT_URI,
                            true,
                            new VolumeButtonObserver(getApplicationContext(), new Handler())
                    );

            IntentFilter filter = new IntentFilter();
            filter.addAction("service.test.btn");
            BroadcastReceiver updateUIRReceiver2 = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //UI update here
                    Log.d("apero", intent.getStringExtra("number"));
                    mediaIsActivate.setValue(false);
                }
            };
            registerReceiver(updateUIRReceiver2, filter);
        }


        // listener on media is activate

        mediaIsActivate.observe(this, mediaIsActivate -> {
            if (!mediaIsActivate) {
                //end the media player
                Log.i("desactivé", "mediaIsActivate a été désactivé");
                Intent stopIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
                getApplicationContext().stopService(stopIntent);

                // if is from snooze we remove one and plan a new delay
                if (type.equals("snooze")) {
                    if (alarm.getSnooze().getSnoozeLimit() == 0) {
                        alarm.getSnooze().setActivated(false);
                    } else {
                        alarm.getSnooze().setSnoozeLimit(alarm.getSnooze().getSnoozeLimit() - 1);
                    }
                    alarm.getFrequency().setNextRing(LocalDateTime.now().plusMinutes(alarm.getSnooze().getSnoozeSecTime()));
                } else {
                    // from dismiss
                    if (alarm.getFrequency().getWeekSchedule().size() == 0) {
                        alarm.setActivate(false); // deactivate alarm for an onetime alarm
                    } else {
                        int dayBetween = 0;
                        Boolean[] days = alarm.getAlarmFrequency().getAllWeek().toArray(new Boolean[0]);
                        Duration totalDuration = AlarmUtils.getDurationNextGoOff(
                                alarm.getTimeToGoOff(),
                                days);
                        // to avoid that the alarm go off at the same time
                        if (totalDuration.getSeconds() == 0) {
                            int currentDay = LocalDate.now().getDayOfWeek().getValue();
                            int index = currentDay;
                            // check next day
                            do {
                                index += 1;
                                index = index % 7;
                            } while (index != currentDay && !days[index]);
                            dayBetween = ((index - currentDay) + 7) % 7;
                            if (dayBetween == 0) {
                                dayBetween = 7;
                            }
                        }
                        alarm.getAlarmFrequency().setNextRing(LocalDateTime.now().plus(totalDuration).plusDays(dayBetween));

                    }
                    // add wakeup check if we need it
                    if (alarm.getWakeupCheck().getActivate()) {
                        OneTimeWorkRequest workAlarmRing =
                                new OneTimeWorkRequest.Builder(AlarmGoOffWorker.class)
                                        .setInitialDelay(alarm.getWakeupCheck().getDelayAfterDismiss(), TimeUnit.SECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                                        .addTag("alarm_wakeupcheck")
                                        .setInputData((new Data.Builder()).putInt("id", alarm.getId()).build())
                                        .build();
                        WorkManager.getInstance(getApplicationContext()).enqueue(workAlarmRing);
                    }
                }
                // update room
                addAlarmViewModel.updateAlarm(alarm);
                // the ui program the next worker when the ui will be sets
            }
        });
    }
}


