package com.cqyanyu.backing.ui.net;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface XICallback<T> {

    /**
     * 失败
     */
    void onFail(String msg);

    /**
     * 结束
     */
    void onFinished();
}
