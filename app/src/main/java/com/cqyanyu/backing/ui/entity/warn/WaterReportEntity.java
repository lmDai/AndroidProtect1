package com.cqyanyu.backing.ui.entity.warn;

import java.io.Serializable;

/**
 * 告警上报详情
 * Created by Administrator on 2017/7/13.
 */
public class WaterReportEntity implements Serializable {


    /**
     * typestr : 火灾自动报警控制器
     * reportdate : 1506483480
     * handledate : 0
     * ishandle : 0
     * buildId : 3629
     * oid : 1033
     * sn : 01240000
     * position : 1
     * affairstr : 系统管理员登录
     */

    private String typestr;
    private int reportdate;
    private int handledate;
    private int ishandle;
    private String buildId;
    private String unitid;
    private int oid;
    private String sn;
    private String position;
    private String affairstr;

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public int getReportdate() {
        return reportdate;
    }

    public void setReportdate(int reportdate) {
        this.reportdate = reportdate;
    }

    public int getHandledate() {
        return handledate;
    }

    public void setHandledate(int handledate) {
        this.handledate = handledate;
    }

    public int getIshandle() {
        return ishandle;
    }

    public void setIshandle(int ishandle) {
        this.ishandle = ishandle;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }


    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
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

    public String getAffairstr() {
        return affairstr;
    }

    public void setAffairstr(String affairstr) {
        this.affairstr = affairstr;
    }
}
