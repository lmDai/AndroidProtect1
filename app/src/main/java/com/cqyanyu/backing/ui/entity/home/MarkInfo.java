package com.cqyanyu.backing.ui.entity.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/22.
 */

public class MarkInfo implements Serializable {
    private String name;
    private String describe;
    private double latitude;
    private double longitude;

    public MarkInfo() {
    }

    public MarkInfo(String name, String describe, double latitude, double longitude) {
        this.name = name;
        this.describe = describe;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
