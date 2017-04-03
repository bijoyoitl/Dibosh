package com.optimalbd.dibosh.Model;

/**
 * Created by ripon on 1/10/2017.
 */

public class NotificationTime {

    private int id;
    private String tableId;
    private String eventId;
    private long time;

    public NotificationTime(String tableId, String eventId, long time) {
        this.tableId = tableId;
        this.eventId = eventId;
        this.time = time;
    }

    public NotificationTime(int id, String tableId, String eventId, long time) {
        this.id = id;
        this.tableId = tableId;
        this.eventId = eventId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTableId() {
        return tableId;
    }

    public String getEventId() {
        return eventId;
    }

    public long getTime() {
        return time;
    }
}
