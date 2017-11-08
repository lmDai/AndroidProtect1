package com.cqyanyu.backing.ui.entity.login;

import com.cqyanyu.mvpframework.bean.UserInfoEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class UserInfo extends UserInfoEntity implements Serializable {
    private String phone;
    private String password;

    private boolean result;
    private String info;
    private String userid;
    private String unitid;
    private String roleid;
    private boolean isnode;

    private List<PermissionBean> permission;

    /**
     * userid : 35
     * unitid : 3477
     * roleid : 45
     * platformname : 大数据安全预警平台
     * isnode : false
     * permission : []
     */


    public boolean isnode() {
        return isnode;
    }

    public void setIsnode(boolean isnode) {
        this.isnode = isnode;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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

    public List<PermissionBean> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionBean> permission) {
        this.permission = permission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public static class PermissionBean implements Serializable {
        private String pid;
        private String oid;
        private String menu;
        private int type;
        private String status;
        private String menuid;
        private String userid;

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

        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getMenuid() {
            return menuid;
        }

        public void setMenuid(String menuid) {
            this.menuid = menuid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
