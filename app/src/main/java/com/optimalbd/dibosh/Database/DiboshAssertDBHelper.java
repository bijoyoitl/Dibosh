package com.optimalbd.dibosh.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ripon on 12/19/2016.
 */

public class DiboshAssertDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Dibosh.sqlite";
    private static final int DATABASE_VERSION = 2;
    private static final String SP_KEY_DB_VER = "db_ver";

    public static final String IDAYS_TABLE_NAME = "inrernational_days";
    public static final String IDAYS_ID = "id";
    public static final String IDAYS_NAME_EN = "idays_name_eng";
    public static final String IDAYS_NAME_BN = "idays_name_ban";
    public static final String IDAYS_DETAILS_EN = "idays_details_eng";
    public static final String IDAYS_DETAILS_BN = "idays_details_ban";
    public static final String IDAYS_DATE_EN = "idays_date_eng";
    public static final String IDAYS_DATE_BN = "idays_date_ban";
    public static final String IDAYS_DATE = "idays_date";
    public static final String IDAYS_OCCASION_EN = "idays_occasion_eng";
    public static final String IDAYS_OCCASION_BN = "idays_occasion_ban";
    public static final String IDAYS_MONTH_NUMBER = "idays_month_number";

    public static final String NDAYS_TABLE_NAME = "national_days";
    public static final String NDAYS_ID = "id";
    public static final String NDAYS_NAME_EN = "ndays_name_eng";
    public static final String NDAYS_NAME_BN = "ndays_name_ban";
    public static final String NDAYS_DETAILS_EN = "ndays_details_eng";
    public static final String NDAYS_DETAILS_BN = "ndays_details_ban";
    public static final String NDAYS_DATE_EN = "ndays_date_eng";
    public static final String NDAYS_DATE_BN = "ndays_date_ban";
    public static final String NDAYS_DATE = "ndays_date";
    public static final String NDAYS_OCCASION_EN = "ndays_occasion_eng";
    public static final String NDAYS_OCCASION_BN = "ndays_occasion_ban";
    public static final String NDAYS_MONTH_NUMBER = "ndays_month_number";

    public static final String QUOTESID_TABLE_NAME = "quotes_id";
    public static final String QUOTESID_ID = "id";
    public static final String QUOTESID_TITLE = "title";

    public static final String QUOTESMSG_TABLE_NAME = "quotes_msg";
    public static final String QUOTESMSG_ID = "id";
    public static final String QUOTESMSG_QUTOES_ID = "q_id";
    public static final String QUOTESMSG_TITLE = "msg";


    public DiboshAssertDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        initialize();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initialize() {
        if (databaseExits()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int dbVersion = sharedPreferences.getInt(SP_KEY_DB_VER, 1);
            if (DATABASE_VERSION != dbVersion) {
                File file = context.getDatabasePath(DATABASE_NAME);
                if (!file.delete()) {
                    Log.w("DB", "Unable to update database");
                }
            }
        } else {
            createDatabase();
        }

    }

    private boolean databaseExits() {
        File file = context.getDatabasePath(DATABASE_NAME);
        return file.exists();
    }

    private void createDatabase() {
        String parentPath = context.getDatabasePath(DATABASE_NAME).getParent();
        String path = context.getDatabasePath(DATABASE_NAME).getPath();

        File file = new File(parentPath);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.w("DB", "Unable to create database directory");
                return;
            }
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = context.getAssets().open(DATABASE_NAME);
            outputStream = new FileOutputStream(path);

            byte[] bytes = new byte[1024];

            int length;
            while ((length = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
            editor.apply();
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
