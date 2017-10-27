package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/18.
 */
public class DutyStatusEntity {


    private int lastisanswervalid;
    private int oid;
    private double lastanswerlatitude;
    private int userid;
    private String lastanswerposition;
    private String answerremark_c;
    private int invalidanswercount;
    private double lastanswerlongitude;
    private int unitid;
    private long lastoperationdate;
    private int direction;
    private int launchcount;
    private int answercount;
    /**
     * unitstr : 重庆厚盾安全技术有限公司
     * userstr : tjm
     */

    private String unitstr;
    private String userstr;


    public int getLaunchcount() {
        return launchcount;
    }

    public void setLaunchcount(int launchcount) {
        this.launchcount = launchcount;
    }

    public int getAnswercount() {
        return answercount;
    }

    public void setAnswercount(int answercount) {
        this.answercount = answercount;
    }


    public int getLastisanswervalid() {
        return lastisanswervalid;
    }

    public void setLastisanswervalid(int lastisanswervalid) {
        this.lastisanswervalid = lastisanswervalid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public double getLastanswerlatitude() {
        return lastanswerlatitude;
    }

    public void setLastanswerlatitude(double lastanswerlatitude) {
        this.lastanswerlatitude = lastanswerlatitude;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLastanswerposition() {
        return lastanswerposition;
    }

    public void setLastanswerposition(String lastanswerposition) {
        this.lastanswerposition = lastanswerposition;
    }

    public String getAnswerremark_c() {
        return answerremark_c;
    }

    public void setAnswerremark_c(String answerremark_c) {
        this.answerremark_c = answerremark_c;
    }

    public int getInvalidanswercount() {
        return invalidanswercount;
    }

    public void setInvalidanswercount(int invalidanswercount) {
        this.invalidanswercount = invalidanswercount;
    }

    public double getLastanswerlongitude() {
        return lastanswerlongitude;
    }

    public void setLastanswerlongitude(double lastanswerlongitude) {
        this.lastanswerlongitude = lastanswerlongitude;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public long getLastoperationdate() {
        return lastoperationdate;
    }

    public void setLastoperationdate(long lastoperationdate) {
        this.lastoperationdate = lastoperationdate;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }

    public String getUserstr() {
        return userstr;
    }

    public void setUserstr(String userstr) {
        this.userstr = userstr;
    }
}
