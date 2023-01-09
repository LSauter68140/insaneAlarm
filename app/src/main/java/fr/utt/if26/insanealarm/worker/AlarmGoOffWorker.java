package fr.utt.if26.insanealarm.worker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import fr.utt.if26.insanealarm.database.InsaneAlarmDatabase;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.ui.stopAlarm.DoingTaskActivity;

public class AlarmGoOffWorker extends Worker {

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
        // none id found
        if (alarmId == -1)
            return new Result.Failure();
        // let's go
        InsaneAlarmDatabase db = InsaneAlarmDatabase.getDatabase(context);
        Alarm alarmToGoOff = db.alarmDao().getAlarmById(alarmId);
        if (alarmToGoOff != null) {
            Ringtone ringtoneTest = RingtoneManager.getRingtone(context, Uri.parse(alarmToGoOff.getSound().getRingtonePath()));
            ringtoneTest.play();
            // snooze

            if (alarmToGoOff.getSnooze().getActivated()) {
                // first we snooze
                Intent doingTaskIntent = new Intent(getApplicationContext(), DoingTaskActivity.class);
                doingTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                doingTaskIntent.putExtra("taskToDo", alarmToGoOff.getSnooze().getTask());
                getApplicationContext().startActivity(doingTaskIntent);
            }
            // snooze
            // task +
            // button listener
            //https://stackoverflow.com/questions/41692747/listener-for-volume-button-key-events

            // controller button

        } else {
            Log.i("c'est l'heure", "de l'apero");
        }

        return new Result.Success();
    }
}
