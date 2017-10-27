package com.cqyanyu.backing.ui.socket.entity;

/**
 * 响应告警推送信息
 * Created by Administrator on 2017/7/31.
 */
public class ResponseAlarmEntity {

    /**
     * oid : 2235
     * deviceid : 5081
     * buildid : 3437
     * unitid : 3366
     * reportdate : 2017-10-20 17:15:39
     * affairstr : 火警
     * typestr :  光电探头
     * sn : 015-005
     * position : 001栋01区 2层 077号  双人房
     * affairid : 1
     * typeid : 166
     * type : 0
     */

    private String oid;
    private String deviceid;
    private String buildid;
    private String unitid;
    private String reportdate;
    private String affairstr;
    private String typestr;
    private String sn;
    private String position;
    private String affairid;
    private String typeid;
    private String type;

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

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getAffairstr() {
        return affairstr;
    }

    public void setAffairstr(String affairstr) {
        this.affairstr = affairstr;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
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

    public String getAffairid() {
        return affairid;
    }

    public void setAffairid(String affairid) {
        this.affairid = affairid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
