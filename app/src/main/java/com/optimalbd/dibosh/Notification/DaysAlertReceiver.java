package com.optimalbd.dibosh.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.optimalbd.dibosh.Database.DiboshNotificationManager;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.Database.IDaysManager;
import com.optimalbd.dibosh.Database.NDaysManager;
import com.optimalbd.dibosh.DayDetailsActivity;
import com.optimalbd.dibosh.MainActivity;
import com.optimalbd.dibosh.Model.IDays;
import com.optimalbd.dibosh.Model.Personal;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.PersonalDetailsActivity;
import com.optimalbd.dibosh.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by engrb on 27-Sep-16.
 */

public class DaysAlertReceiver extends BroadcastReceiver {

    Context context;
    String tableId;
    String eventId;
    IDaysManager iDaysManager;
    NDaysManager nDaysManager;
    EventManager eventManager;
    ArrayList<IDays> daysArrayList;
    ArrayList<PersonalEvent> eventArrayList;

    String contentTitle;
    String contentText;
    String ticker;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        iDaysManager = new IDaysManager(context);
        nDaysManager = new NDaysManager(context);
        eventManager= new EventManager(context);

        daysArrayList = new ArrayList<>();
        eventArrayList = new ArrayList<>();

        tableId = intent.getStringExtra("tableId");
        eventId = intent.getStringExtra("eventId");


        contentTitle = context.getResources().getString(R.string.app_name);

        switch (tableId) {
            case "1":
                daysArrayList = iDaysManager.getAllSingleIDayInfo(eventId);

                break;
            case "2":
                daysArrayList = nDaysManager.getAllSingleNDayInfo(eventId);

                break;
            default:
                eventArrayList = eventManager.getSingleEvent(eventId);
                break;
        }

        if (tableId.equals("1") || tableId.equals("2")) {
            ticker = daysArrayList.get(0).getName_en();
            contentText = "Today "+daysArrayList.get(0).getName_en();
        } else {
            ticker = eventArrayList.get(0).getName() + " `s " + eventArrayList.get(0).getType();
            contentText = "Today "+eventArrayList.get(0).getName() + " `s " + eventArrayList.get(0).getType();
        }

        createNotification(context, contentTitle, ticker, contentText, tableId, eventId);
    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert, String tableId, String eventId) {
        Intent intent;

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        if (tableId.equals("1") || tableId.equals("2")) {
            intent = new Intent(context, DayDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("from","1");
            intent.putExtra("type", tableId);
            intent.putExtra("dayId", eventId);

        } else {
            intent = new Intent(context, PersonalDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("id", eventId);
            intent.putExtra("from","1");
        }

        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);
        Random random1=new Random();
        int s = random1.nextInt(1000);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, s, new Intent[]{intent}, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(bitmap)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, mBuilder.build());

    }
}
