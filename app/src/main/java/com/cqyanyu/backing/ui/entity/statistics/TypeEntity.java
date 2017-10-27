package com.cqyanyu.backing.ui.entity.statistics;

/**
 * Created by Administrator on 2017/7/18.
 */

public class TypeEntity {
    private int type;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
