package com.cqyanyu.backing.ui.entity.warn;

import java.io.Serializable;

/**
 * 水系统
 * Created by Administrator on 2017/7/13.
 */
public class WaterSystemEntity implements Serializable {

    /**
     * val : 215
     * totalcount : 2368
     * unitstr : 邮电学院
     * latitude : 29.537542517266758
     * pid : 10
     * reporteventid : 2
     * buildid : 3586
     * headdate : 1505119257
     * deviceid : 468
     * lastdate : 1506561668
     * maxval : 1000
     * minval : 300
     * buildstr : 21栋学生公寓
     * typeid : 66
     * sn : B13E66F81FE4
     * position : 21号公寓崇文路2号
     * longitude : 106.61819213279718
     */
    private int val;
    private int totalcount;
    private String unitstr;
    private double latitude;
    private String pid;
    private int reporteventid;
    private String buildid;
    private String headdate;
    private int deviceid;
    private int lastdate;
    private String maxval;
    private String minval;
    private String buildstr;
    private int typeid;
    private String sn;
    private String position;
    private double longitude;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getReporteventid() {
        return reporteventid;
    }

    public void setReporteventid(int reporteventid) {
        this.reporteventid = reporteventid;
    }

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }

    public String getHeaddate() {
        return headdate;
    }

    public void setHeaddate(String headdate) {
        this.headdate = headdate;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getLastdate() {
        return lastdate;
    }

    public void setLastdate(int lastdate) {
        this.lastdate = lastdate;
    }

    public String getMaxval() {
        return maxval;
    }

    public void setMaxval(String maxval) {
        this.maxval = maxval;
    }

    public String getMinval() {
        return minval;
    }

    public void setMinval(String minval) {
        this.minval = minval;
    }

    public String getBuildstr() {
        return buildstr;
    }

    public void setBuildstr(String buildstr) {
        this.buildstr = buildstr;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
