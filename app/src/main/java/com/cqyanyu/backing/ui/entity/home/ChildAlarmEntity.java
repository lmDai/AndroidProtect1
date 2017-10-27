package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by personal on 2017/10/16.
 */

public class ChildAlarmEntity {

    /**
     * typestr : 2603防火门监控模块
     * devicecount : 1
     * deviceid : 5044
     */

    private String typestr;
    private int devicecount;
    private String deviceid;

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

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
