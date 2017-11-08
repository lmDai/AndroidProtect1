package com.cqyanyu.backing.ui.socket.entity;

public class EntityUnits {
    private int oid;
    private int pid;
    private int statusid;
    private int typeid;
    private int selevelid;
    private String name;
    private String position;
    private int createmanid;
    private long createdate;
    private String linkman;
    private String linkphone;
    private int childunitcount;
    private int childusercount;
    private int childdevicecount;
    private double longitude;
    private double latitude;
    private double area;
    private String remark;

    public String ToString() {
        return "name:" + name + " position:" + position + " aid:" + Integer.toString(oid) + " pid:"
                + Integer.toString(pid);
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getSelevelid() {
        return selevelid;
    }

    public void setSelevelid(int selevelid) {
        this.selevelid = selevelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCreatemanid() {
        return createmanid;
    }

    public void setCreatemanid(int createmanid) {
        this.createmanid = createmanid;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }


    public int getChildunitcount() {
        return childunitcount;
    }

    public void setChildunitcount(int childunitcount) {
        this.childunitcount = childunitcount;
    }

    public int getChildusercount() {
        return childusercount;
    }

    public void setChildusercount(int childusercount) {
        this.childusercount = childusercount;
    }

    public int getChilddevicecount() {
        return childdevicecount;
    }

    public void setChilddevicecount(int childdevicecount) {
        this.childdevicecount = childdevicecount;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
