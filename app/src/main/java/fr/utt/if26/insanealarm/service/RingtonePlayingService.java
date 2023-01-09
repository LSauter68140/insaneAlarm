package fr.utt.if26.insanealarm.service;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class RingtonePlayingService extends Service {
    private Ringtone ringtone;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("intent", intent.getStringExtra("ringtone-uri"));
        Uri ringtoneUri = Uri.parse(intent.getStringExtra("ringtone-uri"));
        ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        ringtone.play();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (ringtone.isPlaying())
            ringtone.stop();
    }
}
