package com.cqyanyu.backing.ui.socket.entity;

/**
 * socket通讯基础处理
 * Created by Administrator on 2017/7/31.
 */
public class BaseEntity {
    private String type;
    private String cmd;
    private String parameter;

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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
