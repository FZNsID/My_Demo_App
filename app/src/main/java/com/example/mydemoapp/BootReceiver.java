package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.background_service_library.OnBootReceiver;

public final class BootReceiver extends OnBootReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Starts service on boot for receiving notification updates
        ContextCompat.startForegroundService(context, new Intent(context, MyNewService.class));
    }
}
