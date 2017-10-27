package com.cqyanyu.backing.ui.entity.home;

/**
 * Created by Administrator on 2017/7/20.
 */
public class DetailsResourceEntity {
    private double distance;
    private String local;
    private String names;

    public DetailsResourceEntity() {
    }

    public DetailsResourceEntity(double distance, String names, String local) {
        this.distance = distance;
        this.local = local;
        this.names = names;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
