package com.example.mydemoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();

//        startActivityForResult(new Intent(android.provider.Settings.ACTION_BATTERY_SAVER_SETTINGS), 0);
//        boolean isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 0);

        ContextCompat.startForegroundService(getApplicationContext(), new Intent(getApplicationContext(), MyNewService.class));

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class, 16, TimeUnit.MINUTES).addTag("workCheckData").build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork("workCheckData", ExistingPeriodicWorkPolicy.REPLACE, request);
    }

    //        Don't forget to release your wakelock when your purpose ends, or else you might end up draining your users battery pretty fast.
        /*@Override
        public void onDestroy()
        {
            super.onDestroy();
            wakeLock.release();
        }*/

}