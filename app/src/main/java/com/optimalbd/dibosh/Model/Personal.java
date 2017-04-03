package com.optimalbd.dibosh.Model;

/**
 * Created by ripon on 12/20/2016.
 */

public class Personal {

    private String id;
    private String name;
    private String birthDate;
    private boolean mStatus;
    private String maStatus;
    private String m_date;
    private long birthNotificationTime;
    private long marriageNotificationTime;
    private int age;
    private int anniversary;

    public Personal(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Personal(String name, String birthDate, boolean mStatus, String m_date, long birthNotificationTime, long marriageNotificationTime, int age, int anniversary) {
        this.name = name;
        this.birthDate = birthDate;
        this.mStatus = mStatus;
        this.m_date = m_date;
        this.birthNotificationTime = birthNotificationTime;
        this.marriageNotificationTime = marriageNotificationTime;
        this.age = age;
        this.anniversary=anniversary;
    }

    public Personal(String name, String birthDate, String maStatus, String m_date,int age, int anniversary) {
        this.name = name;
        this.birthDate = birthDate;
        this.maStatus = maStatus;
        this.m_date = m_date;
        this.age=age;
        this.anniversary=anniversary;
    }

    public Personal(String id, String name, String birthDate, String maStatus, String m_date, long birthNotificationTime, long marriageNotificationTime,int age, int anniversary) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.maStatus = maStatus;
        this.m_date = m_date;
        this.birthNotificationTime = birthNotificationTime;
        this.marriageNotificationTime = marriageNotificationTime;
        this.age=age;
        this.anniversary=anniversary;
    }


    public String getMaStatus() {
        return maStatus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean ismStatus() {
        return mStatus;
    }

    public String getM_date() {
        return m_date;
    }

    public long getBirthNotificationTime() {
        return birthNotificationTime;
    }

    public long getMarriageNotificationTime() {
        return marriageNotificationTime;
    }

    public int getAge() {
        return age;
    }

    public int getAnniversary() {
        return anniversary;
    }
}

