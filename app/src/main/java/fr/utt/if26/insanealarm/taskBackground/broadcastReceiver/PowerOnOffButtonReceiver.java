package fr.utt.if26.insanealarm.taskBackground.broadcastReceiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerOnOffButtonReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent local = new Intent();
        local.setAction("service.on.off.btn");
        local.putExtra("number", "false");
        context.sendBroadcast(local);
    }
}
