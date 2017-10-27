package com.cqyanyu.backing.ui.entity.warn;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */

public class WarnEntity implements Serializable {

    private int typeid;
    private long reportdate;
    private long handledate;
    private String buildid;
    private String pid;
    private String oid;
    private int affairid;
    private String affairstr;
    private String handlemanid;
    private String deviceid;
    private String typestr;
    private String handleremark;
    private int ishandle;
    private String unitid;
    private String position;
    private String sn;
    private String handletypeid;
    private String unitstr;
    private String buildstr;


    public int getAffairid() {
        return affairid;
    }

    public void setAffairid(int affairid) {
        this.affairid = affairid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public long getReportdate() {
        return reportdate;
    }

    public void setReportdate(long reportdate) {
        this.reportdate = reportdate;
    }

    public long getHandledate() {
        return handledate;
    }

    public void setHandledate(long handledate) {
        this.handledate = handledate;
    }

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAffairstr() {
        return affairstr;
    }

    public void setAffairstr(String affairstr) {
        this.affairstr = affairstr;
    }

    public String getHandlemanid() {
        return handlemanid;
    }

    public void setHandlemanid(String handlemanid) {
        this.handlemanid = handlemanid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public String getHandleremark() {
        return handleremark;
    }

    public void setHandleremark(String handleremark) {
        this.handleremark = handleremark;
    }

    public int getIshandle() {
        return ishandle;
    }

    public void setIshandle(int ishandle) {
        this.ishandle = ishandle;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
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

    public String getHandletypeid() {
        return handletypeid;
    }

    public void setHandletypeid(String handletypeid) {
        this.handletypeid = handletypeid;
    }

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }


    public String getBuildstr() {
        return buildstr;
    }

    public void setBuildstr(String buildstr) {
        this.buildstr = buildstr;
    }
}
