package com.cqyanyu.backing.ui.entity.my;

/**
 * 查岗记录
 * Created by Administrator on 2017/7/13.
 */

public class CheckRecordsEntity {
    /**
     * unitstr : 重庆xx有限公司
     * targetunitid : 3366
     * launchunitid : 3366
     * launchmanid : 66
     * oid : 639
     * answerlongitude : 106.510247
     * launchdate : 1503451551
     * targetmanid : 63
     * isanswer : 0
     * answerdate : 1503451551
     * answerlatitude : 29.619601
     * userstr : xxx
     * answerposition : 中国重庆市渝北区黄山大道中段-7号
     */

    private String unitstr;
    private int targetunitid;
    private int launchunitid;
    private int launchmanid;
    private int oid;
    private double answerlongitude;
    private long launchdate;
    private int targetmanid;
    private int isanswer;
    private int answerdate;
    private double answerlatitude;
    private String userstr;
    private String answerposition;

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }

    public int getTargetunitid() {
        return targetunitid;
    }

    public void setTargetunitid(int targetunitid) {
        this.targetunitid = targetunitid;
    }

    public int getLaunchunitid() {
        return launchunitid;
    }

    public void setLaunchunitid(int launchunitid) {
        this.launchunitid = launchunitid;
    }

    public int getLaunchmanid() {
        return launchmanid;
    }

    public void setLaunchmanid(int launchmanid) {
        this.launchmanid = launchmanid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public double getAnswerlongitude() {
        return answerlongitude;
    }

    public void setAnswerlongitude(double answerlongitude) {
        this.answerlongitude = answerlongitude;
    }

    public long getLaunchdate() {
        return launchdate;
    }

    public void setLaunchdate(long launchdate) {
        this.launchdate = launchdate;
    }

    public int getTargetmanid() {
        return targetmanid;
    }

    public void setTargetmanid(int targetmanid) {
        this.targetmanid = targetmanid;
    }

    public int getIsanswer() {
        return isanswer;
    }

    public void setIsanswer(int isanswer) {
        this.isanswer = isanswer;
    }

    public int getAnswerdate() {
        return answerdate;
    }

    public void setAnswerdate(int answerdate) {
        this.answerdate = answerdate;
    }

    public double getAnswerlatitude() {
        return answerlatitude;
    }

    public void setAnswerlatitude(double answerlatitude) {
        this.answerlatitude = answerlatitude;
    }

    public String getUserstr() {
        return userstr;
    }

    public void setUserstr(String userstr) {
        this.userstr = userstr;
    }

    public String getAnswerposition() {
        return answerposition;
    }

    public void setAnswerposition(String answerposition) {
        this.answerposition = answerposition;
    }
}
