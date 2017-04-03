package com.optimalbd.dibosh.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.optimalbd.dibosh.Model.Personal;

import java.util.ArrayList;

/**
 * Created by ripon on 12/20/2016.
 */

public class PersonalManager {

    private SQLiteDatabase database;
    private DiboshDbHelper diboshDbHelper;
    private Context context;
    private Cursor cursor = null;

    public PersonalManager(Context context) {
        this.context = context;
        diboshDbHelper = new DiboshDbHelper(context);
    }

    public long addPersonalInfo(Personal personal) {

        long success = 0;

        ContentValues values = new ContentValues();
        values.put(DiboshDbHelper.PERSONAL_NAME, personal.getName());
        values.put(DiboshDbHelper.PERSONAL_BIRTHDATE, personal.getBirthDate());
        values.put(DiboshDbHelper.PERSONAL_M_STATUS, personal.ismStatus());
        values.put(DiboshDbHelper.PERSONAL_M_DATE, personal.getM_date());
        values.put(DiboshDbHelper.PERSONAL_BIRTH_NOTIFICATION, personal.getBirthNotificationTime());
        values.put(DiboshDbHelper.PERSONAL_MARRIAGE_NOTIFICATION, personal.getMarriageNotificationTime());
        values.put(DiboshDbHelper.PERSONAL_AGE, personal.getAge());
        values.put(DiboshDbHelper.PERSONAL_ANNIVERSARY, personal.getAnniversary());

        try {
            database = diboshDbHelper.getWritableDatabase();
            success = database.insert(DiboshDbHelper.PERSONAL_TABLE_NAME, null, values);
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

    public long UpdatePersonalInfo(Personal personal, String id) {

        long success = 0;

        try {
            database = diboshDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DiboshDbHelper.PERSONAL_NAME, personal.getName());
            values.put(DiboshDbHelper.PERSONAL_BIRTHDATE, personal.getBirthDate());
            values.put(DiboshDbHelper.PERSONAL_M_STATUS, personal.ismStatus());
            values.put(DiboshDbHelper.PERSONAL_M_DATE, personal.getM_date());
            values.put(DiboshDbHelper.PERSONAL_BIRTH_NOTIFICATION, personal.getBirthNotificationTime());
            values.put(DiboshDbHelper.PERSONAL_MARRIAGE_NOTIFICATION, personal.getMarriageNotificationTime());
            values.put(DiboshDbHelper.PERSONAL_AGE, personal.getAge());
            values.put(DiboshDbHelper.PERSONAL_ANNIVERSARY, personal.getAnniversary());

            success = database.update(DiboshDbHelper.PERSONAL_TABLE_NAME, values, DiboshDbHelper.PERSONAL_ID + "=?", new String[]{id});
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

    public ArrayList<Personal> getPersonalInfo() {

        ArrayList<Personal> personalArrayList = null;
        Personal personal;

        try {
            personalArrayList = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();
            cursor = database.query(DiboshDbHelper.PERSONAL_TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_ID));
                        String name = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_NAME));
                        String birth = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_BIRTHDATE));
                        String mStatus = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_M_STATUS));
                        String mDate = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_M_DATE));
                        long birthNTime = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_BIRTH_NOTIFICATION));
                        long marriageNTime = cursor.getLong(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_MARRIAGE_NOTIFICATION));
                        int age= cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_AGE));
                        int anniversary= cursor.getInt(cursor.getColumnIndex(DiboshDbHelper.PERSONAL_ANNIVERSARY));

                        personal = new Personal(id,name, birth, mStatus, mDate,birthNTime,marriageNTime,age,anniversary);
                        personalArrayList.add(personal);

                    } while (cursor.moveToNext());
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

        return personalArrayList;
    }


    public boolean isEmpty() {

        boolean empty = true;
        database = diboshDbHelper.getReadableDatabase();
        Cursor cur = database.rawQuery("SELECT COUNT(*) FROM " + DiboshDbHelper.PERSONAL_TABLE_NAME, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt(0) == 0);
        }
        cur.close();

        if (database.isOpen()) {
            database.close();
        }

        return empty;
    }


}
