package com.example.myapplication1;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class TimeNotification extends BroadcastReceiver  {
    private static final int NOTIFY_ID = 101;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private NotificationManager notificationManager;

    public void onReceive(Context context, Intent intent) {
        if(MainActivity2.myDBManager.getNoti()==1) {
            MainActivity2.NotiMinuts = MainActivity2.myDBManager.getMin();
            MainActivity2.NotiHours = MainActivity2.myDBManager.getHours();

            if (MainActivity2.myDBManager.testNoti() > 0) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setAutoCancel(false)
                                .setSmallIcon(R.mipmap.log)
                                .setWhen(System.currentTimeMillis())
                                .setContentTitle("Пора готовиться")
                                .setContentText("Вам пора пройти план на сегодня");

                createChannelIfNeeded(notificationManager);
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, MainActivity2.NotiHours);
                calendar.set(Calendar.MINUTE, MainActivity2.NotiMinuts);
                calendar.set(Calendar.SECOND, 0);
                calendar.add(calendar.DATE, 1);

                AlarmManager am1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intentt = new Intent(context, TimeNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intentt, PendingIntent.FLAG_UPDATE_CURRENT);
                am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, MainActivity2.NotiHours);
                calendar.set(Calendar.MINUTE, MainActivity2.NotiMinuts);
                calendar.set(Calendar.SECOND, 0);
                calendar.add(calendar.DATE, 1);

                AlarmManager am1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intentt = new Intent(context, TimeNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intentt, PendingIntent.FLAG_UPDATE_CURRENT);
                am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }


        }

    }

    private void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}

