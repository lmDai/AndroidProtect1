package com.cqyanyu.backing.ui.entity.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/18.
 */

public class AlarmNHEntity implements Serializable {
    private String unitstr;
    private String oid;
    private String deviceid;
    private String buildid;
    private String unitid;
    private String typestr;
    private String affairstr;
    private String position;
    private String sn;
    private String buildstr;
    private int affairid;
    private long reportdate;
    private double longitude;
    private double latitude;
    private int ishandle;

    public int getIshandle() {
        return ishandle;
    }

    public void setIshandle(int ishandle) {
        this.ishandle = ishandle;
    }

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public String getAffairstr() {
        return affairstr;
    }

    public void setAffairstr(String affairstr) {
        this.affairstr = affairstr;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBuildstr() {
        return buildstr;
    }

    public void setBuildstr(String buildstr) {
        this.buildstr = buildstr;
    }

    public int getAffairid() {
        return affairid;
    }

    public void setAffairid(int affairid) {
        this.affairid = affairid;
    }

    public long getReportdate() {
        return reportdate;
    }

    public void setReportdate(long reportdate) {
        this.reportdate = reportdate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
