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

public class NDaysManager {

    private SQLiteDatabase database;
    private DiboshAssertDBHelper diboshAssertDBHelper;
    private Context context;
    private Cursor cursor = null;

    public NDaysManager(Context context) {
        this.context = context;
        diboshAssertDBHelper = new DiboshAssertDBHelper(context);
    }

    public ArrayList<IDays> getAllNDays() {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.NDAYS_TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_MONTH_NUMBER)));

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
    public ArrayList<IDays> getAllNDays(String ids) {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        String orderQuery;
        if (ids.equals("")){
            orderQuery=null;
        }else {
            orderQuery= DiboshDbHelper.orderQuery(ids,DiboshAssertDBHelper.NDAYS_ID);
        }

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.NDAYS_TABLE_NAME, null, null, null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_MONTH_NUMBER)));

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

    public ArrayList<IDays> getAllSingleNDayInfo(String id) {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.NDAYS_TABLE_NAME, null, DiboshAssertDBHelper.NDAYS_ID + "='" + id + "'", null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_MONTH_NUMBER)));
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
    public ArrayList<IDays> getAllSingleMonthnDay(int id,String ids) {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        String orderQuery;
        if (ids.equals("")){
            orderQuery=null;
        }else {
            orderQuery=DiboshDbHelper.orderQuery(ids,DiboshAssertDBHelper.NDAYS_ID);
        }

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.NDAYS_TABLE_NAME, null, DiboshAssertDBHelper.NDAYS_MONTH_NUMBER + "='" + id + "'", null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_MONTH_NUMBER)));
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
    public ArrayList<IDays> getAllUpComingNDayS(String ids) {
        ArrayList<IDays> iDaysArrayList = new ArrayList<>();
        IDays iDays;

        String orderQuery;
        if (ids.equals("")) {
            orderQuery = null;
        } else {
            orderQuery = DiboshDbHelper.orderQuery(ids, DiboshAssertDBHelper.NDAYS_ID);
        }

        try {
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.NDAYS_TABLE_NAME, null, DiboshAssertDBHelper.NDAYS_ID + " in(" + ids + ")", null, null, null, orderQuery);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        iDays = new IDays(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_NAME_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DETAILS_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE_BN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_DATE)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_EN)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_OCCASION_BN)),
                                cursor.getInt(cursor.getColumnIndex(DiboshAssertDBHelper.NDAYS_MONTH_NUMBER)));
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
