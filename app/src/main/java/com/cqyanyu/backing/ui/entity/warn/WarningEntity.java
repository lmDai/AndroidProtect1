package com.cqyanyu.backing.ui.entity.warn;

import java.io.Serializable;

/**
 * 预警
 * Created by Administrator on 2017/7/13.
 */
public class WarningEntity implements Serializable {

    private double val;
    private long reportdate;
    private long handledate;
    private String buildid;
    private int pid;
    private int reporteventid;
    private String oid;
    private int handlemanid;
    private String deviceid;
    private String handleremark;
    private int ishandle;
    private String unitid;
    private String sn;
    private int handletypeid;
    private int typeid;
    /**
     * val : 230
     * unitstr : 重庆厚盾安全技术有限公司
     * reportdate : 1504254209
     * handledate : 0
     * buildid : 3437
     * oid : 5712
     * deviceid : 10
     * unitid : 3366
     * buildstr : 客户接待中心
     * sn : 1
     * position : 重庆市渝中区瑞天路10号
     */

    private String unitstr;

    private String buildstr;

    private String position;

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getReporteventid() {
        return reporteventid;
    }

    public void setReporteventid(int reporteventid) {
        this.reporteventid = reporteventid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getHandlemanid() {
        return handlemanid;
    }

    public void setHandlemanid(int handlemanid) {
        this.handlemanid = handlemanid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getHandletypeid() {
        return handletypeid;
    }

    public void setHandletypeid(int handletypeid) {
        this.handletypeid = handletypeid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
