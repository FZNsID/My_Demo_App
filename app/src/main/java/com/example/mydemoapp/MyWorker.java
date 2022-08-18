package com.example.mydemoapp;

import static android.content.ContentValues.TAG;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        myWork();
        return Result.success();
    }

    private void myWork() {
        Log.d("Worker Statement", "Checking");

        if (!isMyServiceRunning(getApplicationContext(), MyNewService.class)) {
            Log.d("Worker Statement", "Start");
            ContextCompat.startForegroundService(getApplicationContext(), new Intent(getApplicationContext(), MyNewService.class));
        }else {
            Log.d("Worker Statement", "Already Running");
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }catch (Exception e){
            Log.e(TAG, "isMyServiceRunning: ",e );
        }
        return false;
    }

}
