package fr.utt.if26.insanealarm.broadcastReceiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerOnOffButtonReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            //End service when user phone screen off

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            //Start service when user phone screen on

        }
        Log.i("button bisss", "ecran on/off");
        Intent local = new Intent();
        local.setAction("service.on.off.btn");
        local.putExtra("number", "false");
        context.sendBroadcast(local);
    }
}
