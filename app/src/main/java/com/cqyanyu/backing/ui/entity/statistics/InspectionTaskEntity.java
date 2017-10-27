package com.cqyanyu.backing.ui.entity.statistics;

/**
 * 巡检任务
 * Created by Administrator on 2017/7/12.
 */

public class InspectionTaskEntity {


    /**
     * date : 1504195200
     * inspectionmanstr : 符美均
     * unitstr : 重庆厚盾安全技术有限公司
     * devicecount : 9
     * problemcount : 0
     * buildid : 3456
     * oid : 613
     * isenable : 0
     * inspectionmanid : 65
     * inspectioncount : 0
     * unitid : 3366
     * buildstr : 2号仓库
     * problemhandlecount : 0
     */

    private long date;
    private String inspectionmanstr;
    private String unitstr;
    private int devicecount;
    private int problemcount;
    private int buildid;
    private int oid;
    private int isenable;
    private int inspectionmanid;
    private int inspectioncount;
    private int unitid;
    private String buildstr;
    private int problemhandlecount;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getInspectionmanstr() {
        return inspectionmanstr;
    }

    public void setInspectionmanstr(String inspectionmanstr) {
        this.inspectionmanstr = inspectionmanstr;
    }

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
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

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getIsenable() {
        return isenable;
    }

    public void setIsenable(int isenable) {
        this.isenable = isenable;
    }

    public int getInspectionmanid() {
        return inspectionmanid;
    }

    public void setInspectionmanid(int inspectionmanid) {
        this.inspectionmanid = inspectionmanid;
    }

    public int getInspectioncount() {
        return inspectioncount;
    }

    public void setInspectioncount(int inspectioncount) {
        this.inspectioncount = inspectioncount;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public String getBuildstr() {
        return buildstr;
    }

    public void setBuildstr(String buildstr) {
        this.buildstr = buildstr;
    }

    public int getProblemhandlecount() {
        return problemhandlecount;
    }

    public void setProblemhandlecount(int problemhandlecount) {
        this.problemhandlecount = problemhandlecount;
    }
}
