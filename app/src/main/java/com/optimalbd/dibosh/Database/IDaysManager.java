package com.optimalbd.dibosh.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.optimalbd.dibosh.Model.IDays;

import java.util.ArrayList;

/**
 * Created by ripon on 12/19/2016.
 */

public class IDaysManager {

    private SQLiteDatabase database;
    private DiboshAssertDBHelper diboshAssertDBHelper;
    private Context context;
    private Cursor cursor = null;

    public IDaysManager(Context context) {
        this.context = context;
        diboshAssertDBHelper = new DiboshAssertDBHelper(context);
    }

    public ArrayList<IDays> getAllInternationalDays() {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.IDAYS_TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_MONTH_NUMBER)));

                        iDaysArrayList.add(iDays);

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
        return iDaysArrayList;
    }
    public ArrayList<IDays> getAllInternationalDays(String ids) {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        String orderQuery;
        if (ids.equals("")) {
            orderQuery = null;
        } else {
            orderQuery = DiboshDbHelper.orderQuery(ids, DiboshAssertDBHelper.IDAYS_ID);
        }

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.IDAYS_TABLE_NAME, null, null, null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_MONTH_NUMBER)));

                        iDaysArrayList.add(iDays);

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
        return iDaysArrayList;
    }

    public ArrayList<IDays> getAllIntUpComingDays(String ids) {
        ArrayList<IDays> iDaysArrayList=null;
        IDays iDays;

        String orderQuery;
        if (ids.equals("")) {
            orderQuery = null;
        } else {
            orderQuery = DiboshDbHelper.orderQuery(ids, DiboshAssertDBHelper.IDAYS_ID);
        }


        try {
            iDaysArrayList = new ArrayList<>();
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.IDAYS_TABLE_NAME, null, DiboshAssertDBHelper.IDAYS_ID + " in(" + ids + ")", null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_MONTH_NUMBER)));

                        iDaysArrayList.add(iDays);

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
//            cursor.close();
        }
        return iDaysArrayList;
    }

    public ArrayList<IDays> getAllSingleIDayInfo(String dayId) {

        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.IDAYS_TABLE_NAME, null, DiboshAssertDBHelper.IDAYS_ID + " = '" + dayId + "'", null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_MONTH_NUMBER)));

                        iDaysArrayList.add(iDays);

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
        return iDaysArrayList;
    }
    public ArrayList<IDays> getAllSingleMonthDays(int dayId,String ids) {

        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        String orderQuery;
        if (ids.equals("")){
            orderQuery=null;
        }else {
            orderQuery=DiboshDbHelper.orderQuery(ids,DiboshAssertDBHelper.IDAYS_ID);
        }

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.IDAYS_TABLE_NAME, null, DiboshAssertDBHelper.IDAYS_MONTH_NUMBER + " = '" + dayId + "'", null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.IDAYS_MONTH_NUMBER)));

                        iDaysArrayList.add(iDays);

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
        return iDaysArrayList;
    }
}
