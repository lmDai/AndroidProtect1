package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/20.
 */

public class TargetEntity {

    private String targetmanid;
    private String targetunitid;

    public TargetEntity() {
    }

    public TargetEntity(String targetmanid, String targetunitid) {
        this.targetmanid = targetmanid;
        this.targetunitid = targetunitid;
    }

    public String getTargetmanid() {
        return targetmanid;
    }

    public void setTargetmanid(String targetmanid) {
        this.targetmanid = targetmanid;
    }

    public String getTargetunitid() {
        return targetunitid;
    }

    public void setTargetunitid(String targetunitid) {
        this.targetunitid = targetunitid;
    }
}
