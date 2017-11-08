package com.cqyanyu.backing.ui.entity.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/18.
 */

public class WarnNHEntity implements Serializable {
    private String unitstr;
    private String oid;
    private String deviceid;
    private String buildid;
    private String unitid;
    private int totalcount;
    private String typeid;
    private long reportlastdate;
    private int reportlasteeventid;
    private String sn;
    private String position;
    private int val;
    private String buildstr;
    private double longitude;
    private double latitude;
    /**
     * reportlastdate : 1509412674
     * isrecovery : 0
     * reportevent : 2
     * latitude : 29.53489505417068
     * oid : 34
     * typestr : 水压监测终端
     * maxval : 1000
     * minval : 200
     * typeid : 66
     * durationsec : 227633
     * longitude : 106.61516484322955
     */

    private int isrecovery;
    private int reportevent;
    private String typestr;
    private int maxval;
    private int minval;
    private int durationsec;


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

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public long getReportlastdate() {
        return reportlastdate;
    }

    public void setReportlastdate(long reportlastdate) {
        this.reportlastdate = reportlastdate;
    }

    public int getReportlasteeventid() {
        return reportlasteeventid;
    }

    public void setReportlasteeventid(int reportlasteeventid) {
        this.reportlasteeventid = reportlasteeventid;
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

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getBuildstr() {
        return buildstr;
    }

    public void setBuildstr(String buildstr) {
        this.buildstr = buildstr;
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


    public int getIsrecovery() {
        return isrecovery;
    }

    public void setIsrecovery(int isrecovery) {
        this.isrecovery = isrecovery;
    }

    public int getReportevent() {
        return reportevent;
    }

    public void setReportevent(int reportevent) {
        this.reportevent = reportevent;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public int getMaxval() {
        return maxval;
    }

    public void setMaxval(int maxval) {
        this.maxval = maxval;
    }

    public int getMinval() {
        return minval;
    }

    public void setMinval(int minval) {
        this.minval = minval;
    }

    public int getDurationsec() {
        return durationsec;
    }

    public void setDurationsec(int durationsec) {
        this.durationsec = durationsec;
    }
}
