package com.example.pcd.finald;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by NICE on 22-04-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        showNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("title"));
        Log.d("data", remoteMessage.getData().get("message") + remoteMessage.getData().get("title"));
    }

    private void showNotification(String message, String title) {

        //Intent i = new Intent(this, Mainpage.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent notificationIntent = new Intent(getApplicationContext(), dr_desh.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(uri)
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(intent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }
}

