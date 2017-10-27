package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/18.
 */
public class TotalSetEntity {

    /**
     * typestr : 灭火器
     * devicecount : 3072
     * nextnode : 4
     * typeid : 18
     */

    private String typestr;
    private int devicecount;
    private int nextnode;
    private String typeid;

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public int getDevicecount() {
        return devicecount;
    }

    public void setDevicecount(int devicecount) {
        this.devicecount = devicecount;
    }

    public int getNextnode() {
        return nextnode;
    }

    public void setNextnode(int nextnode) {
        this.nextnode = nextnode;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }
}
