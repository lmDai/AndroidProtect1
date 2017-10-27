package com.cqyanyu.backing.ui.adapter.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用ViewPagerAdapter
 * Created by zj on 2016/11/24.
 */
class XPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    /**
     * 初始化
     *
     * @param fm 管理器
     */
    public XPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 设置数据
     *
     * @param mList 集合
     */
    public void setList(@NonNull List<Fragment> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    /**
     * 添加单条数据
     *
     * @param array 单条数据
     */
    public void add(@NonNull Fragment... array) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.addAll(Arrays.asList(array));
        notifyDataSetChanged();
    }

    /**
     * 添加集合数据
     *
     * @param list 集合数据
     */
    public void addAll(@NonNull List<Fragment> list) {
        if (mList != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
}
