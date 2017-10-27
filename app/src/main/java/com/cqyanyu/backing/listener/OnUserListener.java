package com.cqyanyu.backing.listener;

import com.cqyanyu.backing.ui.entity.home.UserManageEntity;

/**
 * 获取词条
 * Created by Administrator on 2017/7/13.
 */
public interface OnUserListener {
    void success(UserManageEntity entity);

    void fail();
}
