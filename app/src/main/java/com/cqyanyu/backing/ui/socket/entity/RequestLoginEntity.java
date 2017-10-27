package com.cqyanyu.backing.ui.socket.entity;

/**
 * 请求登录实体类
 * Created by Administrator on 2017/7/31.
 */
public class RequestLoginEntity {

    private String type;
    private String cmd;
    private ParameterBean parameter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean parameter) {
        this.parameter = parameter;
    }

    public static class ParameterBean {
        private MsgBean msg;
        private String sn;

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
    }

    public static class MsgBean {
        private String userid;
        private String terminaltype;
        private String loginname;
        private String unitid;
        private String roleid;
        private String loginpwd;
        private String pushtype;
        private String devicetoken;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTerminaltype() {
            return terminaltype;
        }

        public void setTerminaltype(String terminaltype) {
            this.terminaltype = terminaltype;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getUnitid() {
            return unitid;
        }

        public void setUnitid(String unitid) {
            this.unitid = unitid;
        }

        public String getRoleid() {
            return roleid;
        }

        public void setRoleid(String roleid) {
            this.roleid = roleid;
        }

        public String getLoginpwd() {
            return loginpwd;
        }

        public void setLoginpwd(String loginpwd) {
            this.loginpwd = loginpwd;
        }

        public String getPushtype() {
            return pushtype;
        }

        public void setPushtype(String pushtype) {
            this.pushtype = pushtype;
        }

        public String getDevicetoken() {
            return devicetoken;
        }

        public void setDevicetoken(String devicetoken) {
            this.devicetoken = devicetoken;
        }
    }
}
