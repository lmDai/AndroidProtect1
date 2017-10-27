package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/13.
 */

public class EntryEntity {
    public String result;

    private String name;
    private String pid;
    private String oid;
    private String childcount;
    private String status;

    public EntryEntity() {
    }

    public EntryEntity(String oid, String name) {
        this.name = name;
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getChildcount() {
        return childcount;
    }

    public void setChildcount(String childcount) {
        this.childcount = childcount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
