package com.optimalbd.dibosh.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.optimalbd.dibosh.Model.Quotes;

import java.util.ArrayList;

/**
 * Created by ripon on 1/7/2017.
 */

public class QuotesManager {

    private SQLiteDatabase database;
    private DiboshAssertDBHelper diboshAssertDBHelper;
    private Context context;
    private Cursor cursor = null;

    public QuotesManager(Context context) {
        this.context = context;
        diboshAssertDBHelper = new DiboshAssertDBHelper(context);
    }

    public ArrayList<Quotes> getAllQuotes(String id) {
        ArrayList<Quotes> quotesArrayList = null;
        Quotes quotes;
        try {
            quotesArrayList = new ArrayList<>();
            database = diboshAssertDBHelper.getReadableDatabase();
            cursor = database.query(DiboshAssertDBHelper.QUOTESMSG_TABLE_NAME, null, DiboshAssertDBHelper.QUOTESMSG_QUTOES_ID + " = " + id, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        quotes = new Quotes(
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.QUOTESMSG_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.QUOTESMSG_QUTOES_ID)),
                                cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.QUOTESMSG_TITLE)));

                        quotesArrayList.add(quotes);
//                        String q= cursor.getString(cursor.getColumnIndex(DiboshAssertDBHelper.QUOTESMSG_ID));

                    } while (cursor.moveToNext());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            database.close();
            cursor.close();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
            cursor.close();
        }
        return quotesArrayList;

    }
}
