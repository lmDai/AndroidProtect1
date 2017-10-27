package com.cqyanyu.backing.ui.entity.home;

/**
 * 告警实体类
 * Created by Administrator on 2017/7/18.
 */
public class AlarEntity {

    /**
     * date : 0
     * problemhandle : 0
     * otherhandle : 0
     * othercount : 5
     * problemcount : 0
     * alarmhandle : 0
     * unitid : 3366
     * warnhandle : 0
     * oid : 1
     * alarmcount : 0
     * warncount : 9
     */

    private long date;
    private int problemhandle;
    private int otherhandle;
    private int othercount;
    private int problemcount;
    private int alarmhandle;
    private String unitid;
    private int warnhandle;
    private String oid;
    private int alarmcount;
    private int warncount;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getProblemhandle() {
        return problemhandle;
    }

    public void setProblemhandle(int problemhandle) {
        this.problemhandle = problemhandle;
    }

    public int getOtherhandle() {
        return otherhandle;
    }

    public void setOtherhandle(int otherhandle) {
        this.otherhandle = otherhandle;
    }

    public int getOthercount() {
        return othercount;
    }

    public void setOthercount(int othercount) {
        this.othercount = othercount;
    }

    public int getProblemcount() {
        return problemcount;
    }

    public void setProblemcount(int problemcount) {
        this.problemcount = problemcount;
    }

    public int getAlarmhandle() {
        return alarmhandle;
    }

    public void setAlarmhandle(int alarmhandle) {
        this.alarmhandle = alarmhandle;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public int getWarnhandle() {
        return warnhandle;
    }

    public void setWarnhandle(int warnhandle) {
        this.warnhandle = warnhandle;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getAlarmcount() {
        return alarmcount;
    }

    public void setAlarmcount(int alarmcount) {
        this.alarmcount = alarmcount;
    }

    public int getWarncount() {
        return warncount;
    }

    public void setWarncount(int warncount) {
        this.warncount = warncount;
    }
}
