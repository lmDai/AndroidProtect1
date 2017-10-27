package com.cqyanyu.backing.ui.entity.home;

/**
 * 用户管理
 * Created by Administrator on 2017/7/11.
 */
public class UserManageEntity {
    private boolean clickable;

    private String password;
    private String statusid;
    private String phone;
    private String roleid;
    private String createmanid;
    private String name;
    private String createdate;
    private String unitid;
    private String remark;
    private String oid;
    private String unitstr;
    private String rolestr;
    private String userstr;

    public String getUnitstr() {
        return unitstr;
    }

    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }

    public String getRolestr() {
        return rolestr;
    }

    public void setRolestr(String rolestr) {
        this.rolestr = rolestr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusid() {
        return statusid;
    }

    public void setStatusid(String statusid) {
        this.statusid = statusid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getCreatemanid() {
        return createmanid;
    }

    public void setCreatemanid(String createmanid) {
        this.createmanid = createmanid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
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

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public String getUserstr() {
        return userstr;
    }

    public void setUserstr(String userstr) {
        this.userstr = userstr;
    }
}
