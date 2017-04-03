package com.optimalbd.dibosh.Model;

/**
 * Created by ripon on 1/7/2017.
 */

public class Quotes {

    private String id;
    private String qutoes_id;
    private String msg;

    public Quotes(String id, String qutoes_id, String msg) {
        this.id = id;
        this.qutoes_id = qutoes_id;
        this.msg = msg;
    }

    public Quotes(String qutoes_id, String msg) {
        this.qutoes_id = qutoes_id;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public String getQutoes_id() {
        return qutoes_id;
    }

    public String getMsg() {
        return msg;
    }
}
