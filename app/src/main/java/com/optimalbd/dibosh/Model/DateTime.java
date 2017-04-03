package com.optimalbd.dibosh.Model;

/**
 * Created by ripon on 12/29/2016.
 */

public class DateTime {

    private String id;
    private String eventId;
    private String eventDate;
    private long date;

    public DateTime(String eventId, String eventDate, long date) {
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.date = date;
    }

    public DateTime(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public long getDate() {
        return date;
    }
}
