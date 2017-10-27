package com.cqyanyu.backing.ui.net;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface XICallbackEntity<T> extends XICallback<T> {

    /**
     * 成功
     *
     * @param entity 数据
     */
    void onSuccess(T entity);

    /**
     * 获取类型
     *
     * @return 类型
     */
    Class getClazz();
}
