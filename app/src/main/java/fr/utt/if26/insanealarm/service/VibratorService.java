package fr.utt.if26.insanealarm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.Nullable;

public class VibratorService extends Service {

    private Vibrator v;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000};
        v.vibrate(VibrationEffect.createWaveform(pattern, 0));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        v.cancel();
    }
}
