package com.cqyanyu.backing.ui.socket.entity;

/**
 * Created by personal on 2017/10/19.
 */

public class RequestLocationEntity {

    /**
     * cmd : 143
     * type : 135
     * parameter : {"sn":12,"msg":{"userid":205,"unitid":3366,"devicetoken":23,"reportdate":1501210977,"longitude":35.1111111,"latitude":3366.11111}}
     */

    private String cmd;
    private String type;
    private ParameterBean parameter;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean parameter) {
        this.parameter = parameter;
    }

    public static class ParameterBean {
        /**
         * sn : 12
         * msg : {"userid":205,"unitid":3366,"devicetoken":23,"reportdate":1501210977,"longitude":35.1111111,"latitude":3366.11111}
         */

        private String sn;
        private MsgBean msg;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

    }

    public static class MsgBean {
        /**
         * userid : 205
         * unitid : 3366
         * devicetoken : 23
         * reportdate : 1501210977
         * longitude : 35.1111111
         * latitude : 3366.11111
         */

        private String userid;
        private String unitid;
        private String devicetoken;
        private String reportdate;
        private double longitude;
        private double latitude;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUnitid() {
            return unitid;
        }

        public void setUnitid(String unitid) {
            this.unitid = unitid;
        }

        public String getDevicetoken() {
            return devicetoken;
        }

        public void setDevicetoken(String devicetoken) {
            this.devicetoken = devicetoken;
        }

        public String getReportdate() {
            return reportdate;
        }

        public void setReportdate(String reportdate) {
            this.reportdate = reportdate;
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
    }
}
