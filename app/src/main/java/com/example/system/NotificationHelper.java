package com.example.system;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper
{

    public static  void displayNotification(Context context,String t,String b)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,Maintenance_Report.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("!! Emergency !!")
                .setContentText("The is a new emergency")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //.setAutoCancel(true)
        //.setSound(defaultSoundUri)
        //.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notificationBuilder.build());
    }

}
