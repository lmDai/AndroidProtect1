package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/18.
 */
public class MyTaskEntity {
    private long date;
    private int devicecount;
    private int problemcount;
    private String buildid;
    private long pushdate;
    private String oid;
    private int type;
    private int ispush;
    private String pushresult;
    private int inspectioncount;
    private String inspectionmanid;
    private String unitid;
    private int problemhandlecount;
    private int isenable;
    /**
     * inspectionmanstr : 邓洪中
     * unitstr : 重庆厚盾安全技术有限公司
     * buildstr : 行政办公楼
     */
    private String inspectionmanstr;
    private String unitstr;
    private String buildstr;


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

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
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

    public String getPushresult() {
        return pushresult;
    }

    public void setPushresult(String pushresult) {
        this.pushresult = pushresult;
    }

    public int getInspectioncount() {
        return inspectioncount;
    }

    public void setInspectioncount(int inspectioncount) {
        this.inspectioncount = inspectioncount;
    }

    public String getInspectionmanid() {
        return inspectionmanid;
    }

    public void setInspectionmanid(String inspectionmanid) {
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

    public int getIsenable() {
        return isenable;
    }

    public void setIsenable(int isenable) {
        this.isenable = isenable;
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

    public String getBuildstr() {
        return buildstr;
    }

    public void setBuildstr(String buildstr) {
        this.buildstr = buildstr;
    }
}
