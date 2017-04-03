package com.optimalbd.dibosh.Ulility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

/**
 * Created by ripon on 12/19/2016.
 */

public class DiboshSharePreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    public DiboshSharePreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("dibosh", Context.MODE_PRIVATE);
    }

    public void saveLanguage(String lang) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", lang);
        editor.apply();
        editor.commit();
    }

    public void saveLastUpdatedDate(String date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_update", date);
        editor.apply();
        editor.commit();
    }

    public void saveViewAsMode(String mode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mode", mode);
        editor.apply();
        editor.commit();
    }

    public void saveTotalPage(int pages) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pages", pages);
        editor.apply();
        editor.commit();
    }

    public int getTotalPages() {
        return sharedPreferences.getInt("pages", 1);
    }

    public String getLang(String defaultLanguage) {
        return sharedPreferences.getString("lang", defaultLanguage);
    }

    public String getLanguage() {
        return sharedPreferences.getString("lang", "");
    }

    public String getViewAsMode() {
        return sharedPreferences.getString("mode", "");
    }

    public String getLastUpdatedDate() {
        return sharedPreferences.getString("last_update", "");
    }
}
