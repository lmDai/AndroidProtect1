package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/7.
 */

public class HomeBean {
    private int key_id;
    private int res_id;
    private String name;

    public HomeBean(int key_id, String name, int res_id) {
        this.key_id = key_id;
        this.name = name;
        this.res_id = res_id;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
