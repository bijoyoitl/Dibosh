package com.optimalbd.dibosh.Ulility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ripon on 12/21/2016.
 */

public class DateShow {

    public static long longDate23(String dateS) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm:ss", Locale.ENGLISH);
            java.util.Date date = sdf.parse(dateS);
            calendar.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static long longDate(String dateS) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            java.util.Date date = sdf.parse(dateS);


            calendar.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static long longBirthDate(String dateS) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
            java.util.Date date = sdf.parse(dateS);


            calendar.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static String currentDate() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());
        String todayDate = formattedDate + " " + "23:59:59";

        return todayDate;
    }

    public static String currentDate12() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());
        String todayDate = formattedDate + " " + "12:00:00";

        return todayDate;
    }

    public static String dayMonthFormat(int day, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        SimpleDateFormat month_date = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);
        String month_name = month_date.format(cal.getTime());

        return month_name;
    }

    public static String dateFormat(int day, int month, int year) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        SimpleDateFormat month_date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String month_name = month_date.format(cal.getTime());

        return month_name;
    }

    public static String Hour12(String data) throws ParseException {

        DateFormat f1 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH); //HH for hour of the day (0 - 23)
        Date d = f1.parse(data);
        DateFormat f2 = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        return f2.format(d);
    }

    public static String currentDay() {

        String todayDate;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        todayDate = day + "/" + month + "/" + year;

        return todayDate;
    }
}
