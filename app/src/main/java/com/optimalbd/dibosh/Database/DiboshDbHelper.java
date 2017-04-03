package com.optimalbd.dibosh.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by ripon on 12/20/2016.
 */

public class DiboshDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dibosh_per.sqlite";
    private static final int DATABASE_VERSION = 2;

    public static final String PERSONAL_TABLE_NAME = "personal";
    public static final String PERSONAL_ID = "id";
    public static final String PERSONAL_NAME = "name";
    public static final String PERSONAL_BIRTHDATE = "birth_date";
    public static final String PERSONAL_M_STATUS = "m_status";
    public static final String PERSONAL_M_DATE = "m_date";
    public static final String PERSONAL_BIRTH_NOTIFICATION = "b_notification";
    public static final String PERSONAL_MARRIAGE_NOTIFICATION = "m_notification";
    public static final String PERSONAL_AGE = "age";
    public static final String PERSONAL_TYPE = "type";
    public static final String PERSONAL_ANNIVERSARY = "anniversary";

    public static final String EVENT_TABLE_NAME = "event";
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_TYPE = "type";
    public static final String EVENT_TYPE_POSITION = "type_position";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_MONTH_DATE = "month_date";
    public static final String EVENT_LONG_DATE = "long_date";
    public static final String EVENT_RELATIONSHIP = "relationship";
    public static final String EVENT_RELATIONSHIP_POSITION = "relationship_position";
    public static final String EVENT_DAY = "day";
    public static final String EVENT_MONTH = "month";

    public static final String IDAY_TIME_TABLE_NAME = "iday_time";
    public static final String IDAY_ID = "id";
    public static final String IDAY_EVENT_ID = "event_id";
    public static final String IDAY_EVENT_DATE = "event_date";
    public static final String IDAY_DATE = "date";
//    public static final String EVENT_RELATIONSHIP = "relationship";

    public static final String NDAY_TIME_TABLE_NAME = "nday_time";
    public static final String NDAY_ID = "id";
    public static final String NDAY_EVENT_ID = "event_id";
    public static final String NDAY_EVENT_DATE = "event_date";
    public static final String NDAY_DATE = "date";
//    public static final String EVENT_RELATIONSHIP = "relationship";

    public static final String PERSONAL_TIME_TABLE_NAME = "event_time";
    public static final String PERSONAL_TIME_ID = "id";
    public static final String PERSONAL_EVENT_ID = "event_id";
    public static final String PERSONAL_EVENT_DATE = "event_date";
    public static final String PERSONAL_DATE = "date";
//    public static final String EVENT_RELATIONSHIP = "relationship";


    public static final String NOTIFICATION_TABLE_NAME = "notification";
    public static final String NOTIFICATION_ID = "id";
    public static final String NOTIFICATION_TABLE_ID = "tableId";
    public static final String NOTIFICATION_EVENT_ID = "eventId";
    public static final String NOTIFICATION_TIME = "time";


    public static final String HOLIDAY_TABLE_NAME = "holiday";
    public static final String HOLIDAY_ID = "id";
    public static final String HOLIDAY_DAY_ID = "day_id";
    public static final String HOLIDAY_NAME_EN = "name_en";
    public static final String HOLIDAY_NAME_BN = "name_bn";
    public static final String HOLIDAY_DATE_EN = "date_en";
    public static final String HOLIDAY_DATE_BN = "date_bn";
    public static final String HOLIDAY_DATE = "date";
    public static final String HOLIDAY_DAY_EN = "day_en";
    public static final String HOLIDAY_DAY_BN = "day_bn";

    private static final String HOLIDAY_TABLE = "CREATE TABLE IF NOT EXISTS " + HOLIDAY_TABLE_NAME + "("
            + HOLIDAY_ID + " integer primary key autoincrement not null,"
            + HOLIDAY_DAY_ID + " varchar,"
            + HOLIDAY_NAME_EN + " varchar,"
            + HOLIDAY_NAME_BN + " varchar,"
            + HOLIDAY_DATE_EN + " varchar,"
            + HOLIDAY_DATE_BN + " varchar,"
            + HOLIDAY_DATE + " varchar,"
            + HOLIDAY_DAY_EN + " varchar,"
            + HOLIDAY_DAY_BN + " varchar);";

    private static final String NOTIFICATION_TABLE = "CREATE TABLE IF NOT EXISTS " + NOTIFICATION_TABLE_NAME + "("
            + NOTIFICATION_ID + " integer primary key autoincrement not null,"
            + NOTIFICATION_TABLE_ID + " varchar,"
            + NOTIFICATION_EVENT_ID + " varchar,"
            + NOTIFICATION_TIME + " varchar);";


    private static final String PERSONAL_TABLE = "CREATE TABLE IF NOT EXISTS " + PERSONAL_TABLE_NAME + "("
            + PERSONAL_ID + " integer primary key autoincrement not null,"
            + PERSONAL_NAME + " varchar,"
            + PERSONAL_BIRTHDATE + " varchar,"
            + PERSONAL_M_STATUS + " varchar,"
            + PERSONAL_M_DATE + " varchar,"
            + PERSONAL_BIRTH_NOTIFICATION + " varchar,"
            + PERSONAL_MARRIAGE_NOTIFICATION + " varchar,"
            + PERSONAL_AGE + " integer,"
            + PERSONAL_ANNIVERSARY + " integer);";

    private static final String EVENT_TABLE = "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_NAME + "("
            + EVENT_ID + " integer primary key autoincrement not null,"
            + EVENT_NAME + " varchar,"
            + EVENT_TYPE + " varchar,"
            + EVENT_TYPE_POSITION + " varchar,"
            + EVENT_DATE + " varchar,"
            + EVENT_MONTH_DATE + " varchar,"
            + EVENT_LONG_DATE + " varchar,"
            + EVENT_RELATIONSHIP + " varchar,"
            + EVENT_RELATIONSHIP_POSITION + " varchar,"
            + EVENT_DAY + " varchar,"
            + EVENT_MONTH + " varchar);";

    private static final String IDAY_TIME = "CREATE TABLE IF NOT EXISTS " + IDAY_TIME_TABLE_NAME + "("
            + IDAY_ID + " integer primary key autoincrement not null,"
            + IDAY_EVENT_ID + " varchar,"
            + IDAY_EVENT_DATE + " varchar,"
            + IDAY_DATE + " varchar);";

    private static final String NDAY_TIME = "CREATE TABLE IF NOT EXISTS " + NDAY_TIME_TABLE_NAME + "("
            + NDAY_ID + " integer primary key autoincrement not null,"
            + NDAY_EVENT_ID + " varchar,"
            + NDAY_EVENT_DATE + " varchar,"
            + NDAY_DATE + " varchar);";

    private static final String PERSONAL_TIME = "CREATE TABLE IF NOT EXISTS " + PERSONAL_TIME_TABLE_NAME + "("
            + PERSONAL_TIME_ID + " integer primary key autoincrement not null,"
            + PERSONAL_EVENT_ID + " varchar,"
            + PERSONAL_EVENT_DATE + " varchar,"
            + PERSONAL_DATE + " varchar);";


    public DiboshDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(PERSONAL_TABLE);
        sqLiteDatabase.execSQL(EVENT_TABLE);
        sqLiteDatabase.execSQL(IDAY_TIME);
        sqLiteDatabase.execSQL(NDAY_TIME);
        sqLiteDatabase.execSQL(PERSONAL_TIME);
        sqLiteDatabase.execSQL(NOTIFICATION_TABLE);
        sqLiteDatabase.execSQL(HOLIDAY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IDAY_TIME_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NDAY_TIME_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HOLIDAY_TABLE_NAME);
        onCreate(db);
    }

    public static String orderQuery(String ids, String rowName) {

        final String[] tokens = ids.split(",");
        String finalOrder = "CASE " + rowName;

        for (int i = 1; i <= tokens.length; i++) {
            finalOrder += " WHEN '" + tokens[i - 1] + "' THEN " + i;
        }
        finalOrder += " END ," + rowName;

        return finalOrder;
    }
}
