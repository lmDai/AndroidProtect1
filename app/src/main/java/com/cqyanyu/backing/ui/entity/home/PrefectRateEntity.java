package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/18.
 */

public class PrefectRateEntity {
    private String name;
    private int good;
    private int total;

    public PrefectRateEntity() {
    }

    public PrefectRateEntity(String name, int good, int total) {
        this.name = name;
        this.good = good;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
