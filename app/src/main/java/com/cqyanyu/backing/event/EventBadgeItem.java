package com.cqyanyu.backing.event;

import java.util.Observer;

/**
 * Created by personal on 2017/10/25.
 */

public class EventBadgeItem {

    private static EventBadgeItem sInstance;
    private BadgeItemObservable observable;

    //双锁单例
    public static EventBadgeItem getInstance() {
        if (sInstance == null) {
            synchronized (EventBadgeItem.class) {
                if (sInstance == null) {
                    sInstance = new EventBadgeItem();
                }
            }
        }
        return sInstance;
    }

    private EventBadgeItem() {
        observable = new BadgeItemObservable();
    }

    //添加 observer 到 observable中
    public void register(Observer o) {
        observable.addObserver(o);
    }

    public void unregister(Observer o) {
        observable.deleteObserver(o);
    }

    //发送更新数据给 observer
    public void post(Object obj) {
        observable.postNewPublication(obj);
    }
}
