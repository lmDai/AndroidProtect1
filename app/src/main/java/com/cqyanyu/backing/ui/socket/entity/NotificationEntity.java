package com.cqyanyu.backing.ui.socket.entity;

/**
 * 消息通知信息
 * Created by Administrator on 2017/8/3.
 */
public class NotificationEntity {
    private int type;////1.火警，2.故障，4.其他 , 3.预警

    public NotificationEntity() {
    }

    public NotificationEntity(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
