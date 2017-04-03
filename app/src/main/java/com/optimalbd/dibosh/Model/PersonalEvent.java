package com.optimalbd.dibosh.Model;

/**
 * Created by ripon on 12/21/2016.
 */

public class PersonalEvent {

    private String id;
    private String name;
    private String type;
    private String date;
    private String monthDate;
    private long longDate;
    private String relationship;
    private int day;
    private int month;
    private String typePosition;
    private String relationPosition;

    public PersonalEvent(String id, String name, String type, String date, String relationship) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.relationship = relationship;
    }

    public PersonalEvent(String name, String type, String date, String relationship) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.relationship = relationship;
    }

    public PersonalEvent(String id, String name, String type, String date, String relationship, String typePosition, String relationPosition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.relationship = relationship;
        this.typePosition = typePosition;
        this.relationPosition = relationPosition;
    }

    public PersonalEvent(String id, String name, String type, String date, String monthDate, long longDate, String relationship, String typePosition, String relationPosition,int day,int month) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.monthDate = monthDate;
        this.longDate = longDate;
        this.relationship = relationship;
        this.typePosition = typePosition;
        this.relationPosition = relationPosition;
        this.day=day;
        this.month=month;
    }

    public PersonalEvent(String name, String type, String date, String monthDate, long longDate, String relationship, String typePosition, String relationPosition, int day,int month) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.monthDate = monthDate;
        this.longDate=longDate;
        this.relationship = relationship;
        this.typePosition = typePosition;
        this.relationPosition = relationPosition;
        this.day=day;
        this.month=month;
    }

    public PersonalEvent(String name, String type, String date, String relationship, String typePosition, String relationPosition,int day, int month) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.relationship = relationship;
        this.typePosition = typePosition;
        this.relationPosition = relationPosition;
        this.day=day;
        this.month=month;
    }

    public String getMonthDate() {
        return monthDate;
    }

    public long getLongDate() {
        return longDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getTypePosition() {
        return typePosition;
    }

    public String getRelationPosition() {
        return relationPosition;

    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }
}
