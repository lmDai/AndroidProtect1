package com.cqyanyu.backing.ui.entity.home;

import java.io.Serializable;

/**
 * 消防知识
 * Created by Administrator on 2017/7/18.
 */

public class FireKnowledgeEntity implements Serializable {

    /**
     * date : 1501233240
     * filename : 火灾时人的心理特性
     * filepath : uploadFiles/literature/001.pdf
     * statusid : 0
     * typeid : 0
     * oid : 1
     */

    private long date;
    private String filename;
    private String filepath;
    private int statusid;
    private int typeid;
    private int oid;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
}
