package com.optimalbd.dibosh.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.optimalbd.dibosh.Model.Holiday;

import java.util.ArrayList;

/**
 * Created by ripon on 2/7/2017.
 */

public class HolidayManager {

    private DiboshDbHelper diboshDbHelper;
    private Context context;
    private SQLiteDatabase database;
    private Cursor cursor;

    public HolidayManager(Context context) {
        this.context = context;
        diboshDbHelper = new DiboshDbHelper(context);
    }

    public long addHoliday(Holiday holiday) {
        long success = 0;
        try {
            database = diboshDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DiboshDbHelper.HOLIDAY_DAY_ID, holiday.getId());
            values.put(DiboshDbHelper.HOLIDAY_NAME_EN, holiday.getNameEn());
            values.put(DiboshDbHelper.HOLIDAY_NAME_BN, holiday.getNameBn());
            values.put(DiboshDbHelper.HOLIDAY_DATE_EN, holiday.getDateEn());
            values.put(DiboshDbHelper.HOLIDAY_DATE_BN, holiday.getDateBn());
            values.put(DiboshDbHelper.HOLIDAY_DATE, holiday.getDate());
            values.put(DiboshDbHelper.HOLIDAY_DAY_EN, holiday.getDayEn());
            values.put(DiboshDbHelper.HOLIDAY_DAY_BN, holiday.getDayBn());

            database.insert(DiboshDbHelper.HOLIDAY_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
            database.close();
//            FirebaseCrash.report(e);
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }
        return success;
    }

    public ArrayList<Holiday> getAllHoliday() {

        ArrayList<Holiday> holidayArrayList = null;
        Holiday holiday;

        try {
            holidayArrayList = new ArrayList<>();
            database = diboshDbHelper.getReadableDatabase();
            cursor = database.query(DiboshDbHelper.HOLIDAY_TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_DAY_ID));
                        String name_en = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_NAME_EN));
                        String name_bn = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_NAME_BN));
                        String date_en = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_DATE_EN));
                        String date_bn = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_DATE_BN));
                        String date = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_DATE));
                        String day_en = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_DAY_EN));
                        String day_bn = cursor.getString(cursor.getColumnIndex(DiboshDbHelper.HOLIDAY_DAY_BN));

                        holiday = new Holiday(id, name_en, name_bn, date_en, date_bn, date, day_en, day_bn);
                        holidayArrayList.add(holiday);

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            if (database.isOpen()){
                database.close();
            }
        }finally {
            cursor.close();
            if (database.isOpen()){
                database.close();
            }
        }

        return holidayArrayList;
    }

    public String holidayIdExits(String dayId) {

        int num = 0;
        try {
            database = diboshDbHelper.getReadableDatabase();
            cursor = database.query(DiboshDbHelper.HOLIDAY_TABLE_NAME, null, DiboshDbHelper.HOLIDAY_DAY_ID + "= '" + dayId + "'", null, null, null, null);
            num = cursor.getCount();
            if (num > 0) {
                return "1";
            } else {
                return "0";
            }

        } catch (Exception e) {
            Log.e("error", e + "");
            e.printStackTrace();
            cursor.close();
        } finally {

            cursor.close();
            if (database.isOpen()) {
                database.close();
            }
        }
        return "0";

    }

    public boolean isEmpty() {

        boolean empty = true;
        database = diboshDbHelper.getReadableDatabase();
        Cursor cur = database.rawQuery("SELECT COUNT(*) FROM " + DiboshDbHelper.HOLIDAY_TABLE_NAME, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt(0) == 0);
        }
        cur.close();

        if (database.isOpen()) {
            database.close();
        }

        return empty;

    }

    public void deleteHolidayInfo(String ids){
        try {
            database = diboshDbHelper.getWritableDatabase();
            database.delete(DiboshDbHelper.HOLIDAY_TABLE_NAME, DiboshDbHelper.HOLIDAY_ID + " not in(" + ids + ")", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }

    }


}
