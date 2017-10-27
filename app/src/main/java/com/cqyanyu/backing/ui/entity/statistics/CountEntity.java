package com.cqyanyu.backing.ui.entity.statistics;

/**
 * Created by personal on 2017/10/16.
 */

public class CountEntity {

    /**
     * deviceTotalCount : 5000
     * sInsRate : 0.00
     * alarmCount : 1480
     * unitCount : 107
     * userCount : 31
     */

    private int deviceTotalCount;
    private String sInsRate;
    private int alarmCount;
    private int unitCount;
    private int userCount;

    public int getDeviceTotalCount() {
        return deviceTotalCount;
    }

    public void setDeviceTotalCount(int deviceTotalCount) {
        this.deviceTotalCount = deviceTotalCount;
    }

    public String getSInsRate() {
        return sInsRate;
    }

    public void setSInsRate(String sInsRate) {
        this.sInsRate = sInsRate;
    }

    public int getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(int alarmCount) {
        this.alarmCount = alarmCount;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
