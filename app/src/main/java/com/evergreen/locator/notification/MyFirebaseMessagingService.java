package com.evergreen.locator.notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.evergreen.locator.R;
import com.evergreen.locator.activity.NotificationDetailsActivity;
import com.evergreen.locator.data.AppConstant;
import com.evergreen.locator.data.preference.AppPreference;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > AppConstant.VALUE_ZERO) {
            Map<String, String> params = remoteMessage.getData();

            if (AppPreference.getInstance(MyFirebaseMessagingService.this).getNotificationStatus()) {
                sendNotification(params.get("title"), params.get("message"));
            }
        }
    }

    private void sendNotification(String title, String message) {

        Intent intent = null;
        if (title != null && !title.isEmpty()) {
            intent = new Intent(this, NotificationDetailsActivity.class);
            intent.putExtra(AppConstant.BUNDLE_KEY_TITLE, title);
            intent.putExtra(AppConstant.BUNDLE_KEY_MESSAGE, message);

        }

        if(intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, AppConstant.VALUE_ZERO, intent, PendingIntent.FLAG_ONE_SHOT);

            String CHANNEL_ID = "noti01";

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_static_notification)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setVibrate(new long[]{1000, 1000})
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(mChannel);
                }
            }

            if (notificationManager != null) {
                notificationManager.notify(0, notificationBuilder.build());
            }
        }
    }
}
