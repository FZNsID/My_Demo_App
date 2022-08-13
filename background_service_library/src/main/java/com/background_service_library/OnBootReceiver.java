package com.background_service_library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Starts service on boot for receiving notification updates
        ContextCompat.startForegroundService(context, new Intent(context, BGService.class));
    }
}
