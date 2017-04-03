package com.optimalbd.dibosh.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.optimalbd.dibosh.Model.PersonalEvent;

import java.util.ArrayList;

/**
 * Created by ripon on 12/21/2016.
 */

public class EventManager {

    private SQLiteDatabase database;
    private DiboshDbHelper diboshDbHelper;
    private Context context;
    private Cursor cursor = null;

    public EventManager(Context context) {
        this.context = context;
        diboshDbHelper = new DiboshDbHelper(context);
    }

    public long addEvent(PersonalEvent personalEvent) {

        long success = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.EVENT_NAME, personalEvent.getName());
        values.put(DiboshDbHelper.EVENT_TYPE, personalEvent.getType());
        values.put(DiboshDbHelper.EVENT_TYPE_POSITION, personalEvent.getTypePosition());
        values.put(DiboshDbHelper.EVENT_DATE, personalEvent.getDate());
        values.put(DiboshDbHelper.EVENT_MONTH_DATE, personalEvent.getMonthDate());
        values.put(DiboshDbHelper.EVENT_LONG_DATE, personalEvent.getLongDate());
        values.put(DiboshDbHelper.EVENT_RELATIONSHIP, personalEvent.getRelationship());
        values.put(DiboshDbHelper.EVENT_RELATIONSHIP_POSITION, personalEvent.getRelationPosition());
        values.put(DiboshDbHelper.EVENT_DAY, personalEvent.getDay());
        values.put(DiboshDbHelper.EVENT_MONTH, personalEvent.getMonth());

        try {
            database = diboshDbHelper.getWritableDatabase();
            success = database.insert(DiboshDbHelper.EVENT_TABLE_NAME, null, values);
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

    public ArrayList<PersonalEvent> getAllEvent() {
        ArrayList<PersonalEvent> personalEventArrayList = new ArrayList<>();
        PersonalEvent personalEvent;

        try {
            database = diboshDbHelper.getReadableDatabase();
            cursor = database.query(DiboshDbHelper.EVENT_TABLE_NAME, null, null, null, null, null, DiboshDbHelper.EVENT_LONG_DATE + " ASC");

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {

                        String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_ID));
                        String eventName = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_NAME));
                        String eventType = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_TYPE));
                        String eventTypePos = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_TYPE_POSITION));
                        String eventDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_DATE));
                        String eventMonthDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_MONTH_DATE));
                        long eventLongDate = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.EVENT_LONG_DATE));
//                        Log.e("EM", "e id : " + eventId + " long : " + eventLongDate);
                        String eventRelationship = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_RELATIONSHIP));
                        String eventRelationshipPos = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_RELATIONSHIP_POSITION));

                        int day = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.EVENT_DAY));
                        int month = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.EVENT_MONTH));

                        personalEvent = new PersonalEvent(eventId, eventName, eventType, eventDate, eventMonthDate, eventLongDate, eventRelationship, eventTypePos, eventRelationshipPos,day,month);

                        personalEventArrayList.add(personalEvent);

                    } while (cursor.moveToNext());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            database.close();
            cursor.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
            cursor.close();
        }
        return personalEventArrayList;
    }

    public ArrayList<PersonalEvent> getSingleEvent(String id) {
        ArrayList<PersonalEvent> personalEventArrayList = new ArrayList<>();
        PersonalEvent personalEvent;

        try {
            database = diboshDbHelper.getReadableDatabase();
            cursor = database.query(DiboshDbHelper.EVENT_TABLE_NAME, null, DiboshDbHelper.EVENT_ID + " = '" + id + "'", null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_ID));
                        String eventName = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_NAME));
                        String eventType = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_TYPE));
                        String eventTypePos = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_TYPE_POSITION));
                        String eventDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_DATE));
                        String eventMonthDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_MONTH_DATE));
                        long eventLongDate = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.EVENT_LONG_DATE));
                        String eventRelationship = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_RELATIONSHIP));
                        String eventRelationshipPos = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_RELATIONSHIP_POSITION));
                        int day = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.EVENT_DAY));
                        int month = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.EVENT_MONTH));

                        personalEvent = new PersonalEvent(eventId, eventName, eventType, eventDate, eventMonthDate, eventLongDate, eventRelationship, eventTypePos, eventRelationshipPos,day,month);

                        personalEventArrayList.add(personalEvent);

                    } while (cursor.moveToNext());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            database.close();
            cursor.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
            cursor.close();
        }
        return personalEventArrayList;
    }

    public ArrayList<PersonalEvent> getAllUpComingEvent(String ids) {
        ArrayList<PersonalEvent> personalEventArrayList = new ArrayList<>();
        PersonalEvent personalEvent;

        String orderQuery;
        if (ids.equals("")) {
            orderQuery = null;
        } else {
            orderQuery = DiboshDbHelper.orderQuery(ids, DiboshDbHelper.EVENT_ID);
        }

        try {
            database = diboshDbHelper.getReadableDatabase();
            cursor = database.query(DiboshDbHelper.EVENT_TABLE_NAME, null, DiboshDbHelper.EVENT_ID + " in(" + ids + ")", null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_ID));
                        String eventName = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_NAME));
                        String eventType = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_TYPE));
                        String eventTypePos = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_TYPE_POSITION));
                        String eventDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_DATE));
                        String eventMonthDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_MONTH_DATE));
                        long eventLongDate = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.EVENT_LONG_DATE));
                        String eventRelationship = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_RELATIONSHIP));
                        String eventRelationshipPos = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.EVENT_RELATIONSHIP_POSITION));
                        int day = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.EVENT_DAY));
                        int month = cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.EVENT_MONTH));
                        personalEvent = new PersonalEvent(eventId, eventName, eventType, eventDate, eventMonthDate, eventLongDate, eventRelationship, eventTypePos, eventRelationshipPos,day,month);

                        personalEventArrayList.add(personalEvent);

                    } while (cursor.moveToNext());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            database.close();
            cursor.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
            cursor.close();
        }
        return personalEventArrayList;
    }

    public int getLastInsertId() {
        int index = 0;
        SQLiteDatabase sdb = diboshDbHelper.getReadableDatabase();
        Cursor cursor = sdb.query(
                "sqlite_sequence",
                new String[]{"seq"},
                "name = ?",
                new String[]{DiboshDbHelper.EVENT_TABLE_NAME},
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            index = cursor.getInt(cursor.getColumnIndex("seq"));
        }
        cursor.close();
        return index;
    }

    public String getAllCalenderEventIds(String sDate) {

        String eventIds = "";
        ArrayList<String> eventIdsArrayList = null;

        try {
            eventIdsArrayList = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.PERSONAL_TIME_TABLE_NAME + " where " + DiboshDbHelper.PERSONAL_EVENT_DATE + " = '" + sDate + "'";
            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_EVENT_ID));
                        eventIdsArrayList.add(id);

                }
            }

                for (String ids : eventIdsArrayList) {
                    if (eventIds.equals("")) {
                        eventIds += ids;
                    } else {
                        eventIds += ',' + ids;
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

        return eventIds;
    }


    public long updateEvent(PersonalEvent personalEvent, String id) {
        long s = 0;
        try {
            database = diboshDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DiboshDbHelper.EVENT_NAME, personalEvent.getName());
            values.put(DiboshDbHelper.EVENT_DATE, personalEvent.getDate());
            values.put(DiboshDbHelper.EVENT_MONTH_DATE, personalEvent.getMonthDate());
            values.put(DiboshDbHelper.EVENT_LONG_DATE, personalEvent.getLongDate());
            values.put(DiboshDbHelper.EVENT_TYPE, personalEvent.getType());
            values.put(DiboshDbHelper.EVENT_TYPE_POSITION, personalEvent.getTypePosition());
            values.put(DiboshDbHelper.EVENT_RELATIONSHIP, personalEvent.getRelationship());
            values.put(DiboshDbHelper.EVENT_RELATIONSHIP_POSITION, personalEvent.getRelationPosition());
            values.put(DiboshDbHelper.EVENT_DAY, personalEvent.getDay());
            values.put(DiboshDbHelper.EVENT_MONTH, personalEvent.getMonth());
            s = database.update(DiboshDbHelper.EVENT_TABLE_NAME, values, DiboshDbHelper.EVENT_ID + "=?", new String[]{id});
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


    public long deleteEvent(String id) {
        long s = 0;

        try {
            database = diboshDbHelper.getWritableDatabase();
            s = database.delete(DiboshDbHelper.EVENT_TABLE_NAME, DiboshDbHelper.EVENT_ID + "=?", new String[]{id});
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
