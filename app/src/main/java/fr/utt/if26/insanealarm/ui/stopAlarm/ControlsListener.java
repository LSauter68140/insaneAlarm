package fr.utt.if26.insanealarm.ui.stopAlarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.broadcastReceiver.PowerOnOffButtonReceiver;
import fr.utt.if26.insanealarm.databinding.FragmentControlListenerBinding;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.service.CameraFlashService;
import fr.utt.if26.insanealarm.service.RingtonePlayingService;
import fr.utt.if26.insanealarm.service.VibratorService;
import fr.utt.if26.insanealarm.service.VolumeButtonObserver;
import fr.utt.if26.insanealarm.ui.alarm.AlarmViewModel;
import fr.utt.if26.insanealarm.utils.AlarmUtils;
import fr.utt.if26.insanealarm.worker.AlarmGoOffWorker;

public class ControlsListener extends AppCompatActivity {
    // add listener for each button and wait
    //broadcaster
    PowerOnOffButtonReceiver powerOnOffButtonReceveier;

    public MutableLiveData<Boolean> mediaIsActivate;
    public String type = "";
    public Alarm alarm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmViewModel addAlarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

        FragmentControlListenerBinding binding = FragmentControlListenerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // retrieve info from dismiss/ snooze activity

        Intent getIntent = getIntent();
        type = getIntent.getStringExtra("type");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            alarm = getIntent.getSerializableExtra("alarm", Alarm.class);
        } else {
            alarm = (Alarm) getIntent.getSerializableExtra("alarm");
        }

        // update TV
        String tvTitle;
        String tvWayToStop = "Moyen de désactivation:\n";
        switch (type) {
            case "snooze":
                tvTitle = "Snooze";
                break;
            case "dismiss":
                tvTitle = "Éteindre";
                break;
            case "wakeupcheckup":
                tvTitle = "Oupsi oupsi tu t'es rendormi...";
            default:
                tvTitle = "";
        }
        binding.tvControlListenerTitle.setText(tvTitle);

        mediaIsActivate = new MutableLiveData<>();
        mediaIsActivate.setValue(true);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        boolean activatePowerBtn = Objects.equals(type, "snooze") ?
                alarm.getSnooze().getControl().isPowerButton() :
                alarm.getDismiss().getControl().isPowerButton();

        //on off button
        if (activatePowerBtn) {
            tvWayToStop += "Bouton de mise sous tension \n";
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
            tvWayToStop += "Bouton de volumes\n";
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
                    mediaIsActivate.setValue(false);
                }
            };
            registerReceiver(updateUIRReceiver2, filter);
        }
        // if button on screen
        boolean btnOnScreen = Objects.equals(type, "snooze") ?
                alarm.getSnooze().getControl().isButtonOnScreen() :
                alarm.getDismiss().getControl().isButtonOnScreen();
        if (btnOnScreen) {
            tvWayToStop += "Bouton au mileu de l'écran";
            binding.btnStopAlarm.setOnClickListener(v -> {
                mediaIsActivate.setValue(false);
            });
        } else {
            binding.btnStopAlarm.setVisibility(View.GONE);
        }

        binding.tvDesactivateMode.setText(tvWayToStop);

        // listener on media is activate

        mediaIsActivate.observe(this, mediaIsActivate -> {
            if (!mediaIsActivate) {
                // stop all the alarms services
                getApplicationContext().stopService(new Intent(getApplicationContext(), RingtonePlayingService.class));
                getApplicationContext().stopService(new Intent(getApplicationContext(), VibratorService.class));
                getApplicationContext().stopService(new Intent(getApplicationContext(), CameraFlashService.class));

                updateNotification(alarm);
                // if is from snooze we remove one and plan a new delay
                if (type.equals("snooze")) {
                    // snooze mode
                    if (alarm.getSnooze().getSnoozeLimit() == 0) {
                        alarm.getSnooze().setActivated(false);
                    } else {
                        alarm.getSnooze().setSnoozeLimit(alarm.getSnooze().getSnoozeLimit() - 1);
                    }
                    alarm.getFrequency().setNextRing(LocalDateTime.now().plusMinutes(alarm.getSnooze().getSnoozeSecTime()));
                } else if (type.equals("dismiss")) {
                    // dismiss mode
                    if (alarm.getFrequency().getWeekSchedule().size() == 0) {
                        alarm.setActivate(false); // deactivate alarm for an onetime alarm
                    } else {
                        int dayBetween = 0;
                        Boolean[] days = alarm.getAlarmFrequency().getAllWeek().toArray(new Boolean[0]);
                        Duration totalDuration = AlarmUtils.getDurationNextGoOff(
                                alarm.getTimeToGoOff(),
                                days);
                        // to avoid that the alarm go off at the same time
                        if (totalDuration.toMinutes() < 2) {
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
                        Data.Builder inputData = new Data.Builder();
                        inputData.putInt("id", alarm.getId());
                        inputData.putString("workerType", "wakeupcheck");
                        inputData.build();
                        OneTimeWorkRequest workAlarmRing =
                                new OneTimeWorkRequest.Builder(AlarmGoOffWorker.class)
                                        .setInitialDelay(alarm.getWakeupCheck().getDelayAfterDismiss(), TimeUnit.SECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                                        .addTag("alarm_wakeupcheck")
                                        .setInputData(inputData.build())
                                        .build();
                        WorkManager.getInstance(getApplicationContext()).enqueue(workAlarmRing);
                    }
                }
                // for wake up check we have nothing to do
                // update room
                addAlarmViewModel.updateAlarm(alarm);
                // the ui program the next worker when the ui will be sets
                finish();
                this.moveTaskToBack(true);
            }
        });


    }

    private void updateNotification(Alarm alarm) {
        // we change priority and the user can remove it
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notificationAlarm")
                .setContentTitle(alarm.getName())
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentText(alarm.getLabel())
                .setPriority(NotificationCompat.PRIORITY_LOW);

        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.notify(alarm.getId(), builder.build());
    }
}


