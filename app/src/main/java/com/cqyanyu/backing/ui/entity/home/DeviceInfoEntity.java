package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/18.
 */
public class DeviceInfoEntity {

    private long date;
    private int devicecount;
    private int problemcount;
    private int buildid;
    private long pushdate;
    private String oid;
    private int type;
    private int ispush;
    private int pushresult;
    private int inspectioncount;
    private int inspectionmanid;
    private String unitid;
    private int problemhandlecount;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDevicecount() {
        return devicecount;
    }

    public void setDevicecount(int devicecount) {
        this.devicecount = devicecount;
    }

    public int getProblemcount() {
        return problemcount;
    }

    public void setProblemcount(int problemcount) {
        this.problemcount = problemcount;
    }

    public int getBuildid() {
        return buildid;
    }

    public void setBuildid(int buildid) {
        this.buildid = buildid;
    }

    public long getPushdate() {
        return pushdate;
    }

    public void setPushdate(long pushdate) {
        this.pushdate = pushdate;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIspush() {
        return ispush;
    }

    public void setIspush(int ispush) {
        this.ispush = ispush;
    }

    public int getPushresult() {
        return pushresult;
    }

    public void setPushresult(int pushresult) {
        this.pushresult = pushresult;
    }

    public int getInspectioncount() {
        return inspectioncount;
    }

    public void setInspectioncount(int inspectioncount) {
        this.inspectioncount = inspectioncount;
    }

    public int getInspectionmanid() {
        return inspectionmanid;
    }

    public void setInspectionmanid(int inspectionmanid) {
        this.inspectionmanid = inspectionmanid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public int getProblemhandlecount() {
        return problemhandlecount;
    }

    public void setProblemhandlecount(int problemhandlecount) {
        this.problemhandlecount = problemhandlecount;
    }
}
