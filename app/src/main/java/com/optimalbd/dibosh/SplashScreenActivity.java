package com.optimalbd.dibosh;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.optimalbd.dibosh.Database.DateManager;
import com.optimalbd.dibosh.Database.IDaysManager;
import com.optimalbd.dibosh.Database.NDaysManager;
import com.optimalbd.dibosh.Database.DiboshNotificationManager;
import com.optimalbd.dibosh.Model.DateTime;
import com.optimalbd.dibosh.Model.IDays;
import com.optimalbd.dibosh.Model.NotificationTime;
import com.optimalbd.dibosh.Notification.DaysAlertReceiver;
import com.optimalbd.dibosh.Ulility.DateShow;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends Activity {

    Calendar calendar;
    Context context;

    ArrayList<IDays> iDaysArrayList;
    ArrayList<IDays> nDaysArrayList;

    IDaysManager iDaysManager;
    NDaysManager nDaysManager;
    DateManager dateManager;
    String tableId;
    DateTime dateTime;
    int year;
    DiboshNotificationManager diboshNotificationManager;
    DiboshSharePreference preference;

    long currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        calendar = Calendar.getInstance();

        this.context = this;
        iDaysArrayList = new ArrayList<>();
        nDaysArrayList = new ArrayList<>();

        iDaysManager = new IDaysManager(this);
        nDaysManager = new NDaysManager(this);
        dateManager = new DateManager(this);
        diboshNotificationManager = new DiboshNotificationManager(this);
        preference = new DiboshSharePreference(this);

        iDaysArrayList = iDaysManager.getAllInternationalDays();
        nDaysArrayList = nDaysManager.getAllNDays();

        currentTime = System.currentTimeMillis();
        year = calendar.get(Calendar.YEAR);

        if (preference.getViewAsMode().equals("")) {
            preference.saveViewAsMode("1");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tableId = "1";
                for (IDays iDays : iDaysArrayList) {

                    String eventId = iDays.getId();
                    String date = iDays.getDate() + "/" + year;
                    String time = "23:59:00";
                    String notificationTime = "09:00:00";
                    String finalDate = date + " " + time;
                    String notificationDate = date + " " + notificationTime;

                    Long lDate = DateShow.longDate23(finalDate);
                    Long notificationDateTime = DateShow.longDate23(notificationDate);


                    if (dateManager.eventIdExits(eventId).equals("0")) {
                        dateTime = new DateTime(eventId, date, lDate);
                        dateManager.addIntDate(dateTime);
                    }

                    if (diboshNotificationManager.notificationEventIdExits(tableId, eventId).equals("0")) {
                        NotificationTime notificationTime1 = new NotificationTime(tableId, eventId, notificationDateTime);
                        diboshNotificationManager.addNotification(notificationTime1);

                        if (notificationDateTime > currentTime) {
                            notificationSet(tableId, eventId, notificationDateTime);
                        }


                    }
                }

                for (IDays iDays : nDaysArrayList) {

                    tableId = "2";
                    String eventId = iDays.getId();
                    String date = iDays.getDate() + "/" + year;
                    String time = "23:59:00";
                    String notificationTime = "08:00:00";
                    String finalDate = date + " " + time;
                    String notificationDate = date + " " + notificationTime;

                    Long lDate = DateShow.longDate23(finalDate);
                    Long notificationDateTime = DateShow.longDate23(notificationDate);


                    if (dateManager.nEventIdExits(eventId).equals("0")) {
                        dateTime = new DateTime(eventId, date, lDate);
                        dateManager.addNationalDate(dateTime);
                    }

                    if (diboshNotificationManager.notificationEventIdExits(tableId, eventId).equals("0")) {
                        NotificationTime notificationTime1 = new NotificationTime(tableId, eventId, notificationDateTime);
                        diboshNotificationManager.addNotification(notificationTime1);

                        if (notificationDateTime > currentTime) {
                            notificationSet(tableId, eventId, notificationDateTime);
                        }

                    }

                }
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 3000);
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

}
