package com.background_service_library;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main service that listens for event changes in background and sends signals to the app for updating data
 */

public class BGService extends Service {

    private static final int NOTIFICATION_ID = 21;
    static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    String title = "Title";
    String msg = "Message";


    public BGService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(NOTIFICATION_ID, getServiceNotification("title", "msg"));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    try {
                        isOnline(getApplicationContext());
                        if (isOnline(getApplicationContext())) {
                            startForeground(NOTIFICATION_ID, getServiceNotification("Internet Activity", "Connected"));
                        } else {
                            startForeground(NOTIFICATION_ID, getServiceNotification("Internet Activity", "Not Connected"));
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        startForeground(NOTIFICATION_ID, getServiceNotification("Internet Activity", "Not Connected with an error"+e));
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000);
        return START_STICKY;
    }

    public void updateNotificationForegroundService() {
        Notification mNotificationService = getServiceNotification("", "");
        if (mNotificationService != null) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFICATION_ID, mNotificationService);
        }
    }


    /**
     * This function creates a notification of the global Safelet state (guardian-user, safelet-user) with
     * additional information
     *
     * @return Notification object with new Safelet State.
     */
    @SuppressLint("StringFormatMatches")
    private Notification getServiceNotification(String title, String msg) {
        String channelId = Utils.createNotificationChannel(this, "Test");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);

        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setSmallIcon(R.drawable.demo);
        builder.setAutoCancel(false);
        builder.setFullScreenIntent(null, true);

        // Building the notification
        /*Intent intentActivity = new Intent(getApplicationContext(), MainActivity.class);
        intentActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(
                    this,
                    0, intentActivity,
                    PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(
                    this,
                    0, intentActivity,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }*/

//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
//                intentActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setSilent(true);
//        builder.setContentIntent(pendingIntent);
        return builder.build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    public static boolean isOnline(Context context) {// requires network state
        // access permisstion
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


   /* @SuppressWarnings("unused")
    @Subscribe
    public void onUpdateNotificationSignal(UpdateServiceNotificationEvent signal) {
        BluetoothLog.writeLog(BaseActivity.TAG, "Update Signal Notification (General Update Trigger)");
        updateNotificationForegroundService();
        if (BluetoothState.get().getUpdatePercentage() == 100) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Utils.createNotificationChannel(this,
                    getString(R.string.notification_channel_battery), true));
            builder.setContentTitle(getString(R.string.safelet_notification_state_firmware_update_done_title));
            builder.setContentText(getString(R.string.safelet_notification_state_firmware_update_done_desc));
            builder.setSmallIcon(R.drawable.notification_safelet_message);
            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setAutoCancel(true);
            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(90901, notification);
        }
    }*/


}
