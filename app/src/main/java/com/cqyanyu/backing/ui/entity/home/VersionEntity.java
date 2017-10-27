package com.cqyanyu.backing.ui.entity.home;

/**
 * 版本
 * Created by Administrator on 2017/9/4.
 */

public class VersionEntity {

    /**
     * ret : true
     * ver : 1.1
     * exp : 修正已知bug;增加更新功能
     * url : /uploadFiles/appupdate/cqtss_release.apk
     */

    private String ret;
    private String ver;
    private String exp;
    private String url;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VersionEntity{" +
                "ret='" + ret + '\'' +
                ", ver='" + ver + '\'' +
                ", exp='" + exp + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
