package com.cqyanyu.backing.ui.entity.my;

/**
 * 消防资讯
 * Created by Administrator on 2017/7/13.
 */

public class ConsulationEntity {
    private int img_id;
    private String title;

    public ConsulationEntity(int img_id, String title) {
        this.img_id = img_id;
        this.title = title;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
