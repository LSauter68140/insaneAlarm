package fr.utt.if26.insanealarm.worker;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.database.InsaneAlarmDatabase;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.service.CameraFlashService;
import fr.utt.if26.insanealarm.service.RingtonePlayingService;
import fr.utt.if26.insanealarm.service.VibratorService;
import fr.utt.if26.insanealarm.ui.stopAlarm.ControlsListener;
import fr.utt.if26.insanealarm.ui.stopAlarm.DoingTaskActivity;

public class AlarmGoOffWorker extends Worker {

    private static final String CHANNEL_ID = "";

    public AlarmGoOffWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        Data data = getInputData();
        int alarmId = data.getInt("id", -1);
        String workerType = data.getString("workerType") != null ? data.getString("workerType") : null;
        String typeRing = "";
        // none id found
        if (alarmId == -1)
            return new Result.Failure();
        // let's go
        InsaneAlarmDatabase db = InsaneAlarmDatabase.getDatabase(context);
        Alarm alarmToGoOff = db.alarmDao().getAlarmById(alarmId);
        if (alarmToGoOff != null) {
            HashMap<String, Serializable> extrasIntent = new HashMap<>();
            // need for each case
            extrasIntent.put("alarm", alarmToGoOff);
            // snooze
            if (alarmToGoOff.getSnooze().getActivated()) {
                extrasIntent.put("taskToDo", alarmToGoOff.getSnooze().getTask());
                typeRing = "snooze";
                extrasIntent.put("type", typeRing);
                createNewIntent(DoingTaskActivity.class, extrasIntent);
            } else if (workerType != null) {
                if (workerType.equals("wakeupcheckNotif")) {
                    // wake up check
                    // add notification
                    // create new worker
                    return new Result.Success();
                } else if (workerType.equals("wakeupcheck")) {
                    typeRing = "wakeupcheckup";
                    extrasIntent.put("type", typeRing);
                    createNewIntent(ControlsListener.class, extrasIntent);
                }
            } else {
                // dismiss mode
                extrasIntent.put("taskToDo", alarmToGoOff.getDismiss().getTask());
                typeRing = "dismiss";
                extrasIntent.put("type", typeRing);
                createNewIntent(DoingTaskActivity.class, extrasIntent);
            }
            // start ringtone service
            Intent ringtonePlayingIntent = new Intent(context, RingtonePlayingService.class);
            ringtonePlayingIntent.putExtra("ringtone-uri", Uri.parse(alarmToGoOff.getSound().getRingtonePath()).toString());
            context.startService(ringtonePlayingIntent);

            if (alarmToGoOff.getSound().getNeedVibration()) {
                context.startService(new Intent(context, VibratorService.class));
            }
            if (alarmToGoOff.getSound().getFlashLight() != 0) {
                // activate the flash
                Intent cameraFlashService = new Intent(context, CameraFlashService.class);
                cameraFlashService.putExtra("flashMode", alarmToGoOff.getSound().getFlashLight());
                context.startService(cameraFlashService);
            }
            String typeNotif;
            switch (typeRing) {
                case "snooze":
                    typeNotif = "Snooze";
                    break;
                case "dismiss":
                    typeNotif = "Éteindre";
                    break;
                case "wakeupcheckup":
                    typeNotif = "Juste pour voir si tu dors";
                    break;
                default:
                    typeNotif = "";

            }
            // add corresponding notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notificationAlarm")
                    .setContentTitle(typeNotif + ": " + alarmToGoOff.getName())
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentText(alarmToGoOff.getLabel())
                    .setOngoing(true)
                    .setSilent(true)
                    .setAutoCancel(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notificationAlarm", "name", importance);
            channel.setDescription("description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(alarmId, builder.build());

        } else {
            Log.i("[ERR] Worker", "impossible de démarrer le worker");
        }

        return new Result.Success();
    }

    private void createNewIntent(Class<?> cls, HashMap<String, Serializable> extras) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        for (Map.Entry<String, java.io.Serializable> extra : extras.entrySet()) {
            intent.putExtra(extra.getKey(), extra.getValue());
        }
        getApplicationContext().startActivity(intent);
    }

}
