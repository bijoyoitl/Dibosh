package com.optimalbd.dibosh.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.optimalbd.dibosh.Model.DateTime;

import java.util.ArrayList;

/**
 * Created by ripon on 12/29/2016.
 */

public class DateManager {

    private SQLiteDatabase database;
    private DiboshDbHelper diboshDbHelper;
    private Context context;
    private Cursor cursor = null;

    public DateManager(Context context) {
        this.context = context;
        diboshDbHelper = new DiboshDbHelper(context);
    }

    //Add event id, date and mili date in iDay_time table.
    public long addIntDate(DateTime dateTime) {

        long success = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.IDAY_EVENT_ID, dateTime.getEventId());
        values.put(DiboshDbHelper.IDAY_EVENT_DATE, dateTime.getEventDate());
        values.put(DiboshDbHelper.IDAY_DATE, dateTime.getDate());

        try {
            database = diboshDbHelper.getWritableDatabase();
            success = database.insert(DiboshDbHelper.IDAY_TIME_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            success = 0;
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }
        return success;
    }
    //get all international ids from dateTime table
    public String getAllIntIds() {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();
            String query = "SELECT " + DiboshDbHelper.IDAY_EVENT_ID + " FROM " + DiboshDbHelper.IDAY_TIME_TABLE_NAME + " ORDER BY " + DiboshDbHelper.IDAY_DATE + " asc ";

            cursor = database.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.IDAY_EVENT_ID));
                        dateTimes.add(id);
                    } while (cursor.moveToNext());
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.report(ex);
            database.close();
            cursor.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
//            cursor.close();
        }
        return eventIds;
    }


    //cheek event id from iDay_time table.
    public String eventIdExits(String eventId) {
        database = diboshDbHelper.getReadableDatabase();
        int num = 0;
        try {
            cursor = database.query(DiboshDbHelper.IDAY_TIME_TABLE_NAME, null, DiboshDbHelper.EVENT_ID + "= '" + eventId + "'", null, null, null, null);
            num = cursor.getCount();
            if (num > 0) {
                return "1";
            } else {
                return "0";
            }

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        } finally {
            cursor.close();
            if (database.isOpen()) {
                database.close();
            }
        }
        return "0";

    }


    //////////////
    //Add event id, date and mili date in nDay_time table.
    public long addNationalDate(DateTime dateTime) {

        long success = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.NDAY_EVENT_ID, dateTime.getEventId());
        values.put(DiboshDbHelper.NDAY_EVENT_DATE, dateTime.getEventDate());
        values.put(DiboshDbHelper.NDAY_DATE, dateTime.getDate());

        try {
            database = diboshDbHelper.getWritableDatabase();
            success = database.insert(DiboshDbHelper.NDAY_TIME_TABLE_NAME, null, values);
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

    //cheek event id from nDay_time table.
    public String nEventIdExits(String eventId) {
        database = diboshDbHelper.getReadableDatabase();
        int num = 0;
        try {
            cursor = database.query(DiboshDbHelper.NDAY_TIME_TABLE_NAME, null, DiboshDbHelper.NDAY_EVENT_ID + "= '" + eventId + "'", null, null, null, null);
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
    //get all national ids from dateTime table
    public String getAllNationalIds() {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();
            String query = "SELECT " + DiboshDbHelper.NDAY_EVENT_ID + " FROM " + DiboshDbHelper.NDAY_TIME_TABLE_NAME + " ORDER BY " + DiboshDbHelper.NDAY_DATE + " asc ";

            cursor = database.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.NDAY_EVENT_ID));
                        dateTimes.add(id);
                    } while (cursor.moveToNext());
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
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
//            cursor.close();
        }
        return eventIds;
    }


    //////////////
    //Add event id, date and mili date in personal_time table.
    public long addPersonalDate(DateTime dateTime) {

        long success = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.PERSONAL_EVENT_ID, dateTime.getEventId());
        values.put(DiboshDbHelper.PERSONAL_EVENT_DATE, dateTime.getEventDate());
        values.put(DiboshDbHelper.PERSONAL_DATE, dateTime.getDate());

        try {
            database = diboshDbHelper.getWritableDatabase();
            success = database.insert(DiboshDbHelper.PERSONAL_TIME_TABLE_NAME, null, values);
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

    public long upDatePersonalDateTime(DateTime dateTime, String id) {
        long s = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.PERSONAL_EVENT_ID, dateTime.getEventId());
        values.put(DiboshDbHelper.PERSONAL_EVENT_DATE, dateTime.getEventDate());
        values.put(DiboshDbHelper.PERSONAL_DATE, dateTime.getDate());

        try {
            database = diboshDbHelper.getWritableDatabase();
            s = database.update(DiboshDbHelper.PERSONAL_TIME_TABLE_NAME, values, DiboshDbHelper.PERSONAL_TIME_ID + "=?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            s = 0;
            database.close();
        }

        return s;
    }


    //cheek event id from nDay_time table.
    public String pEventIdExits(String eventId) {
        database = diboshDbHelper.getReadableDatabase();
        int num = 0;
        try {
            cursor = database.query(DiboshDbHelper.PERSONAL_TIME_TABLE_NAME, null, DiboshDbHelper.PERSONAL_EVENT_ID + "= '" + eventId + "'", null, null, null, null);
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

    public long deletePersonalDateTime(String id) {
        long s = 0;

        try {
            database = diboshDbHelper.getWritableDatabase();
            s = database.delete(DiboshDbHelper.PERSONAL_TIME_TABLE_NAME, DiboshDbHelper.PERSONAL_EVENT_ID + "=?", new String[]{id});
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




    ///////////
    //get UpComing int days ids. from iDay_time table
    public String getUpcomingIntDays(long currentDate) {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + DiboshDbHelper.IDAY_TIME_TABLE_NAME + " where " + DiboshDbHelper.IDAY_DATE + " > '" + currentDate + "' ORDER BY " + DiboshDbHelper.IDAY_DATE + " asc " + " limit 4";
            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.IDAY_EVENT_ID));
                    dateTimes.add(eventId);
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
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

    //get Upcomming national days ids. from nDay_time table
    public String getUpcomingNDays(long currentDate) {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.NDAY_TIME_TABLE_NAME + " where " + DiboshDbHelper.NDAY_DATE + " > '" + currentDate + "' ORDER BY " + DiboshDbHelper.NDAY_DATE + " asc " + " limit 4";  //

            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.NDAY_EVENT_ID));

                    dateTimes.add(eventId);
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
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

    //get Upcomming personal days ids. from personal_time table
    public String getUpcomingPDays(long currentDate) {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.PERSONAL_TIME_TABLE_NAME + " where " + DiboshDbHelper.PERSONAL_DATE + " > '" + currentDate + "' ORDER BY " + DiboshDbHelper.PERSONAL_DATE + " asc " + " limit 4";

            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_TIME_ID));

                    dateTimes.add(eventId);
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
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


    ////////////
    //get Today int days ids. from iDay_time table
    public String getTodayIntDays(long time12, long time23) {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.IDAY_TIME_TABLE_NAME + " where " + DiboshDbHelper.IDAY_DATE + " > '" + time12 + "' and " + DiboshDbHelper.IDAY_DATE + " < '" + time23 + "'";
//            Log.e("DM", " query : " + query);

            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.IDAY_EVENT_ID));
                    dateTimes.add(eventId);
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
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
            cursor.close();
        }

        return eventIds;
    }

    //get Today int days ids. from nDay_time table
    public String getTodayNationalDays(long time12, long time23) {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.NDAY_TIME_TABLE_NAME + " where " + DiboshDbHelper.NDAY_DATE + " > '" + time12 + "' and " + DiboshDbHelper.NDAY_DATE + " < '" + time23 + "'";
//            Log.e("DM", " query : " + query);

            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.NDAY_EVENT_ID));
                    dateTimes.add(eventId);
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            database.close();
        }

        return eventIds;
    }

    //get Today personal days ids. from personal_time table
    public String getTodayPersonalDays(long time12, long time23) {
        ArrayList<String> dateTimes;
        String eventIds = "";

        try {
            dateTimes = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + DiboshDbHelper.PERSONAL_TIME_TABLE_NAME + " where " + DiboshDbHelper.PERSONAL_DATE + " > '" + time12 + "' and " + DiboshDbHelper.PERSONAL_DATE + " < '" + time23 + "'";

            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    String eventId = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_TIME_ID));

                    dateTimes.add(eventId);
                }
            }

            for (String id : dateTimes) {
                if (eventIds.equals("")) {
                    eventIds += id;
                } else {
                    eventIds += ',' + id;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            database.close();
        }

        return eventIds;
    }

    public ArrayList<DateTime> getAllLongDate() {
        ArrayList<DateTime> eventLongDateArrayList = null;
        try {
            eventLongDateArrayList = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();

            String query = "SELECT " + DiboshDbHelper.PERSONAL_DATE + " FROM " + DiboshDbHelper.PERSONAL_TIME_TABLE_NAME;
            cursor = database.rawQuery(query, null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    long longDate = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_DATE));
                    DateTime dateTime = new DateTime(longDate);
                    eventLongDateArrayList.add(dateTime);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            database.close();
        }
        return eventLongDateArrayList;
    }

}
