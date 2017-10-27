package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/17.
 */
public class MapChoiceBean {
    private double latitude;
    private double longitude;
    private String name;
    private String describe;
    private boolean checked;

    public MapChoiceBean(double latitude, double longitude, String name, String describe, boolean checked) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.describe = describe;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
}
