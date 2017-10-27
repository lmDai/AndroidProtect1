package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by personal on 2017/10/16.
 */

public class ChildAlarmDetailsEntity {

    /**
     * picpath : ;uploadFiles/deviceImgs/171011135939237.jpg
     * latitude : 29
     * createdate : 1507873654
     * position : 3栋
     * sn : 00000001
     * longitude : 106
     */

    private String picpath;
    private double latitude;
    private String createdate;
    private String position;
    private String sn;
    private double longitude;

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
