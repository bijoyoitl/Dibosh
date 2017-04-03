package com.optimalbd.dibosh.Database;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.optimalbd.dibosh.Model.NotificationTime;

import java.util.ArrayList;

/**
 * Created by ripon on 1/10/2017.
 */

public class DiboshNotificationManager {
    SQLiteDatabase database;
    Context context;
    DiboshDbHelper diboshDbHelper;
    Cursor cursor = null;

    public DiboshNotificationManager(Context context) {
        this.context = context;
        diboshDbHelper = new DiboshDbHelper(context);
    }

    public long addNotification(NotificationTime time) {
        long s = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.NOTIFICATION_EVENT_ID, time.getEventId());
        values.put(DiboshDbHelper.NOTIFICATION_TABLE_ID, time.getTableId());
        values.put(DiboshDbHelper.NOTIFICATION_TIME, time.getTime());

        try {

            database = diboshDbHelper.getWritableDatabase();
            database.insert(DiboshDbHelper.NOTIFICATION_TABLE_NAME, null, values);

        } catch (Exception e) {
            e.printStackTrace();
            s = 0;
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }
        return s;
    }


    public ArrayList<NotificationTime> getAllNotificationDateTime() {

        ArrayList<NotificationTime> notificationTimes = null;

        try {
            notificationTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.NOTIFICATION_TABLE_NAME;
            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.NOTIFICATION_ID));
                    String tableId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.NOTIFICATION_TABLE_ID));
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.NOTIFICATION_EVENT_ID));
                    long time = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.NOTIFICATION_TIME));
                    NotificationTime notificationTime = new NotificationTime(id, tableId, eventId, time);
                    notificationTimes.add(notificationTime);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            database.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }

        return notificationTimes;
    }

    public String getNotificationId(String tableId, String eventId) {
        String id = "";
        try {
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.NOTIFICATION_TABLE_NAME + " where " + DiboshDbHelper.NOTIFICATION_TABLE_ID + " = " + tableId + " AND " + DiboshDbHelper.NOTIFICATION_EVENT_ID + " = " + eventId;
            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    id = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.NOTIFICATION_ID));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            database.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }
        return id;
    }

    public long getNotificationTime(String tableId, String eventId) {
        long id = 0;
        try {
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.NOTIFICATION_TABLE_NAME + " where " + DiboshDbHelper.NOTIFICATION_TABLE_ID + " = " + tableId + " AND " + DiboshDbHelper.NOTIFICATION_EVENT_ID + " = " + eventId;
            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    id = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.NOTIFICATION_TIME));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            database.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }
        return id;
    }

    //cheek event id from iDay_time table.
    public String notificationEventIdExits(String tableId, String eventId) {
        database = diboshDbHelper.getReadableDatabase();
        int num = 0;
        try {
            cursor = database.query(DiboshDbHelper.NOTIFICATION_TABLE_NAME, null, DiboshDbHelper.NOTIFICATION_TABLE_ID + "='" + tableId + "'" + " AND " + DiboshDbHelper.NOTIFICATION_EVENT_ID + "= '" + eventId + "'", null, null, null, null);
            num = cursor.getCount();
            if (num > 0) {
                return "1";
            } else {
                return "0";
            }

        } catch (Exception e) {
            Log.e("error", e + "");
            e.printStackTrace();
        } finally {
            cursor.close();
            if (database.isOpen()) {
                database.close();
            }
        }
        return "0";
    }

    public long UpdatePersonalNotification(NotificationTime notificationTime, String id) {

        long success = 0;

        try {
            database = diboshDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DiboshDbHelper.NOTIFICATION_TABLE_ID, notificationTime.getTableId());
            values.put(DiboshDbHelper.NOTIFICATION_EVENT_ID, notificationTime.getEventId());
            values.put(DiboshDbHelper.NOTIFICATION_TIME, notificationTime.getTime());
            success = database.update(DiboshDbHelper.NOTIFICATION_TABLE_NAME, values, DiboshDbHelper.NOTIFICATION_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }

        return success;
    }

    public long deleteEventNotification(String id) {
        long s = 0;

        try {
            database = diboshDbHelper.getWritableDatabase();
            s = database.delete(DiboshDbHelper.NOTIFICATION_TABLE_NAME, DiboshDbHelper.NOTIFICATION_EVENT_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            s = 0;
            database.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }

        return s;
    }


}
