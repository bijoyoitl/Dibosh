package com.optimalbd.dibosh.Model;

/**
 * Created by ripon on 12/19/2016.
 */

public class IDays {

    private String id;
    private String name_en;
    private String name_bn;
    private String details_en;
    private String details_bn;
    private String date_en;
    private String date_bn;
    private String date;
    private String occasion_eng;
    private String occasion_ban;
    private int month;

    public IDays(String id, String name_en, String name_bn, String details_en, String details_bn, String date_en, String date_bn, String date, String occasion_eng, String occasion_ban, int month) {
        this.id = id;
        this.name_en = name_en;
        this.name_bn = name_bn;
        this.details_en = details_en;
        this.details_bn = details_bn;
        this.date_en = date_en;
        this.date_bn = date_bn;
        this.date = date;
        this.occasion_eng = occasion_eng;
        this.occasion_ban = occasion_ban;
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public String getName_en() {
        return name_en;
    }

    public String getName_bn() {
        return name_bn;
    }

    public String getDetails_en() {
        return details_en;
    }

    public String getDetails_bn() {
        return details_bn;
    }

    public String getDate_en() {
        return date_en;
    }

    public String getDate_bn() {
        return date_bn;
    }

    public String getDate() {
        return date;
    }

    public String getOccasion_eng() {
        return occasion_eng;
    }

    public String getOccasion_ban() {
        return occasion_ban;
    }

    public int getMonth() {
        return month;
    }
}
