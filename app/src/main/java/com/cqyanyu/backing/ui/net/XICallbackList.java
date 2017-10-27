package com.cqyanyu.backing.ui.net;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface XICallbackList<T> extends XICallback<T> {

    /**
     * 成功
     *
     * @param mList 数据
     */
    void onSuccess(List<T> mList);

    /**
     * 获取类型
     *
     * @return 类型
     */
    Class getClazz();
}
