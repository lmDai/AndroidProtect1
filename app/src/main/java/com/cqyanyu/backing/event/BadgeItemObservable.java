package com.cqyanyu.backing.event;

import java.util.Observable;

/**
 * Created by personal on 2017/10/25.
 */

public class BadgeItemObservable extends Observable {
    //自定义方法名
    public void postNewPublication(Object content) {
        //标识内容发生改变,必须调用，否则notify无效
        setChanged();
        //通知所有观察者
        notifyObservers(content);
    }
}
