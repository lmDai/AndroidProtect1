package com.cqyanyu.backing.ui.entity.statistics;

/**
 * Created by Administrator on 2017/8/1.
 */

public class CountBean {
    private int key_id;
    private String name;
    private String title;
    private int res_id;
    private String count;

    public CountBean(int key_id, String name, String title, int res_id, String count) {
        this.key_id = key_id;
        this.name = name;
        this.title = title;
        this.res_id = res_id;
        this.count = count;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
