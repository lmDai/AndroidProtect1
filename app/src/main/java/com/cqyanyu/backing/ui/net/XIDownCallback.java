package com.cqyanyu.backing.ui.net;

/**
 * 下载回调监听
 * Created by Administrator on 2017/7/13.
 */
public interface XIDownCallback extends XICallback {

    /**
     * 成功
     *
     * @param path 数据
     */
    void onSuccess(String path);
}
