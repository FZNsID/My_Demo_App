package com.background_service_library;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public final class Utils {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private Utils() {
        throw new UnsupportedOperationException();
    }


    /**
     * Navigates to network settings in Android system
     *
     * @param context Context in which is called
     */
    public static void showNetworkSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        context.startActivity(intent);
    }


    public static String createNotificationChannel(Context context, String channelName) {
        return createNotificationChannel(context, channelName, false);
    }

    public static String createNotificationChannel(Context context, String channelName, boolean highImportance) {
//        Timber.tag(BaseActivity.TAG).d("".concat("createNotificationChannel"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = String.valueOf(channelName.hashCode());
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager.getNotificationChannel(channelId) == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName,
                        highImportance ? NotificationManager.IMPORTANCE_HIGH : NotificationManager.IMPORTANCE_DEFAULT));

//                notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName,
//                        highImportance ? NotificationManager.IMPORTANCE_DEFAULT : NotificationManager.IMPORTANCE_DEFAULT));
            }
            return channelId;
        } else {
            return channelName;
        }
    }

    public static String createNotificationChannelNew(Context context, String channelName, boolean highImportance) {
//        Timber.tag(BaseActivity.TAG).d("".concat("createNotificationChannel"));
        Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/mysound");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = String.valueOf(channelName.hashCode());
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager.getNotificationChannel(channelId) == null) {
//                notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName,
//                        highImportance ? NotificationManager.IMPORTANCE_DEFAULT : NotificationManager.IMPORTANCE_DEFAULT));

                // New
                NotificationChannel mChannel = new NotificationChannel(channelId
                        , channelName, NotificationManager.IMPORTANCE_HIGH);
//                NotificationChannel mChannel = new NotificationChannel(channelId
//                        , channelName, NotificationManager.IMPORTANCE_DEFAULT);
                mChannel.enableLights(true);
                mChannel.enableVibration(true);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
//                mChannel.setSound(defaultSoundUri, audioAttributes);
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mChannel.setSound(uri, audioAttributes);
                mChannel.setLightColor(Color.GRAY);
                notificationManager.createNotificationChannel(mChannel);
            }
            return channelId;
        } else {
            return channelName;
        }
    }

}
