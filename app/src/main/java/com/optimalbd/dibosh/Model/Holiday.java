package com.optimalbd.dibosh.Model;


/**
 * Created by ripon on 2/6/2017.
 */

public class Holiday {


    private String id;

    private String nameEn;

    private String nameBn;

    private String dateEn;

    private String dateBn;
    private String date;
    private String dayEn;
    private String dayBn;

    public Holiday(String id, String nameEn, String nameBn, String dateEn, String dateBn, String date, String dayEn, String dayBn) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameBn = nameBn;
        this.dateEn = dateEn;
        this.dateBn = dateBn;
        this.date = date;
        this.dayEn = dayEn;
        this.dayBn = dayBn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameBn() {
        return nameBn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }

    public String getDateEn() {
        return dateEn;
    }

    public void setDateEn(String dateEn) {
        this.dateEn = dateEn;
    }

    public String getDateBn() {
        return dateBn;
    }

    public void setDateBn(String dateBn) {
        this.dateBn = dateBn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayEn() {
        return dayEn;
    }

    public void setDayEn(String dayEn) {
        this.dayEn = dayEn;
    }

    public String getDayBn() {
        return dayBn;
    }

    public void setDayBn(String dayBn) {
        this.dayBn = dayBn;
    }
}
