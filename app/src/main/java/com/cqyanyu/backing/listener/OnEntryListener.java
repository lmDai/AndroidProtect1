package com.cqyanyu.backing.listener;

import com.cqyanyu.backing.ui.entity.home.EntryEntity;

import java.util.List;

/**
 * 获取词条
 * Created by Administrator on 2017/7/13.
 */
public interface OnEntryListener {
    void success(List<EntryEntity> entity);

    void fail();
}
