package com.optimalbd.dibosh;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by engrb on 03-Feb-17.
 */

public class DiboshMessagingService extends FirebaseMessagingService {
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String msg = remoteMessage.getNotification().getBody();
        msgNotification(msg);
        super.onMessageReceived(remoteMessage);
    }

    private void msgNotification(String msg) {
        String appPackageName = getPackageName();
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(msg);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
