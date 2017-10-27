package com.cqyanyu.backing.ui.entity.home;

/**
 * 完好率一级
 * Created by personal on 2017/10/17.
 */

public class PerfectRateEntity {

    /**
     * typestr : 上锁
     * totalcount : 24
     */

    private String typestr;
    private int totalcount;
    private String problemid;

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }
}
