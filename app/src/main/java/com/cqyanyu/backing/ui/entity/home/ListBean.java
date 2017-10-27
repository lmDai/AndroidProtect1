package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/17.
 */

public class ListBean {
    private String id;
    private String name;

    public ListBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
