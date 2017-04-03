package com.optimalbd.dibosh.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.optimalbd.dibosh.Database.DiboshNotificationManager;
import com.optimalbd.dibosh.Database.PersonalManager;
import com.optimalbd.dibosh.Model.NotificationTime;
import com.optimalbd.dibosh.Model.Personal;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ripon on 1/28/2017.
 */

public class NotificationSetService extends Service {

    Context context;
    DiboshNotificationManager manager;
    ArrayList<NotificationTime> notificationTimes;
    PersonalManager personalManager;
    ArrayList<Personal> personalArrayList;

    long currentTime;

    @Override
    public void onStart(Intent intent, int startId) {
        this.context = this;

        manager = new DiboshNotificationManager(context);

        personalManager = new PersonalManager(context);
        notificationTimes = new ArrayList<>();
        personalArrayList = new ArrayList<>();

        notificationTimes = manager.getAllNotificationDateTime();
        personalArrayList = personalManager.getPersonalInfo();
        currentTime = System.currentTimeMillis();


        for (int i = 0; i < notificationTimes.size(); i++) {
            if (notificationTimes.get(i).getTime() > currentTime) {
                notificationSet(notificationTimes.get(i).getTableId(), notificationTimes.get(i).getEventId(), notificationTimes.get(i).getTime());
            }
        }


        if (!personalArrayList.isEmpty()) {

            if (personalArrayList.get(0).getBirthNotificationTime() > currentTime) {
                birthNotification(personalArrayList.get(0).getBirthNotificationTime(), "1", personalArrayList.get(0).getAge(), personalArrayList.get(0).getAnniversary());
            }


            if (personalArrayList.get(0).getMarriageNotificationTime() > 0) {
                if (personalArrayList.get(0).getMarriageNotificationTime() > currentTime) {
                    birthNotification(personalArrayList.get(0).getMarriageNotificationTime(), "2", personalArrayList.get(0).getAge(), personalArrayList.get(0).getAnniversary());
                }
            }

        }


    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void notificationSet(String tableId, String eventId, Long time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DaysAlertReceiver.class);
        intent.putExtra("tableId", tableId);
        intent.putExtra("eventId", eventId);
        Random random = new Random();
        int id = random.nextInt(1000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long notification_time = time;

        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, notification_time, pendingIntent);

    }

    private void birthNotification(Long time, String type, int age, int anniversary) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BirthMarriageReceiver.class);
        intent.putExtra("age", age);
        intent.putExtra("type", type);
        if (type.equals("2")) {
            intent.putExtra("mYear", anniversary);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(type), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);


    }


}
