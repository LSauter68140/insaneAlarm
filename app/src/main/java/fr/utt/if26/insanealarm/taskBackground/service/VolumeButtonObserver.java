package fr.utt.if26.insanealarm.taskBackground.service;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;

public class VolumeButtonObserver extends ContentObserver {
    private final AudioManager audioManager;
    Context context;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */

    public VolumeButtonObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    }


    @Override
    public boolean deliverSelfNotifications() {
        return false;
    }

    @Override
    public void onChange(boolean selfChange) {
        Intent local = new Intent();
        local.setAction("service.test.btn");
        local.putExtra("number", "false");
        context.sendBroadcast(local);
    }
}
