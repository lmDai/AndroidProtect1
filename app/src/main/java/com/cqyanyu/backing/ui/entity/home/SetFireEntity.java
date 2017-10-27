package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SetFireEntity {

    /**
     * picpath : ;uploadFiles/deviceImgs/170926150109635.jpg
     * latitude : 0
     * createdate : 1506483401
     * position :
     * sn : 041105
     * longitude : 0
     */

    private String picpath;
    private int latitude;
    private String createdate;
    private String position;
    private String sn;
    private int longitude;

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
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

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
