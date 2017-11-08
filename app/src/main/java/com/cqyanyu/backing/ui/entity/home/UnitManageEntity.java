package com.cqyanyu.backing.ui.entity.home;

import java.io.Serializable;

/**
 * 单位管理实体类
 * Created by Administrator on 2017/7/11.
 */
public class UnitManageEntity implements Serializable {
    public boolean isHide;
    private String area;
    private double latitude;
    private String createmanid;
    private String createdate;
    private String pid;
    private String remark;
    private String oid;
    private String linkphone;
    private String linkman;
    private String selevelid;
    private int childdevicecount;
    private String childusercount;
    private String childbuildcount;

    public String getChildbuildcount() {
        return childbuildcount;
    }

    public void setChildbuildcount(String childbuildcount) {
        this.childbuildcount = childbuildcount;
    }

    private String statusid;
    private String childunitcount;
    private String name;
    private String typeid;
    private String position;
    private double longitude;

    /**
     * unitstr : 研发1号楼
     * latitude : 29.59181185046737
     * pid : 3366
     * oid : 3367
     * selevelid : 36
     * typestr : 楼宇
     * selevelstr : 一般单位
     * childusercount : 0
     * statusid : 0
     * childunitcount : 0
     * typeid : 12
     * longitude : 106.52056429000503
     */
    private String unitstr;
    private String typestr;
    private String selevelstr;
    private String picpath;
    /**
     * punitstr : 重庆交通大学
     */

    private String punitstr;


    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public String getSelevelstr() {
        return selevelstr;
    }

    public void setSelevelstr(String selevelstr) {
        this.selevelstr = selevelstr;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCreatemanid() {
        return createmanid;
    }

    public void setCreatemanid(String createmanid) {
        this.createmanid = createmanid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getSelevelid() {
        return selevelid;
    }

    public void setSelevelid(String selevelid) {
        this.selevelid = selevelid;
    }

    public int getChilddevicecount() {
        return childdevicecount;
    }

    public void setChilddevicecount(int childdevicecount) {
        this.childdevicecount = childdevicecount;
    }

    public String getChildusercount() {
        return childusercount;
    }

    public void setChildusercount(String childusercount) {
        this.childusercount = childusercount;
    }

    public String getStatusid() {
        return statusid;
    }

    public void setStatusid(String statusid) {
        this.statusid = statusid;
    }

    public String getChildunitcount() {
        return childunitcount;
    }

    public void setChildunitcount(String childunitcount) {
        this.childunitcount = childunitcount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPunitstr() {
        return punitstr;
    }

    public void setPunitstr(String punitstr) {
        this.punitstr = punitstr;
    }
}
