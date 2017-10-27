package com.cqyanyu.backing.ui.adapter.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用BaseAdapter
 * Created by zj on 2017/4/19.
 */
public class XBaseAdapter extends BaseAdapter {

    private OnClickListener listener;
    private List<Object> mList;
    private Class clazz;

    /**
     * 初始化
     *
     * @param viewHolder 管理器
     */
    public <T> XBaseAdapter(Class<? extends XViewHolder<T>> viewHolder) {
        this.clazz = viewHolder;
        mList = new ArrayList<>();
    }

    /**
     * 设置数据
     *
     * @param mList 集合
     */
    public void setList(@NonNull List mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    /**
     * 添加单条数据
     *
     * @param array 单条数据
     */
    public void add(@NonNull Object... array) {
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
    public void addAll(@NonNull List list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.addAll(list);
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
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        XViewHolder holder = null;
        if (convertView == null) {
            try {
                Constructor constructor = clazz.getDeclaredConstructor(new Class[]{ViewGroup.class});
                constructor.setAccessible(true);
                holder = (XViewHolder) constructor.newInstance(new Object[]{viewGroup});
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (holder != null) {
                convertView = holder.getRootView();
                holder.onBindView(this, convertView);
                if (listener != null) {
                    holder.setOnClickListener(listener);
                }
                convertView.setTag(holder);
            }
        } else {
            holder = (XViewHolder) convertView.getTag();
        }
        if (holder != null) {
            holder.onBindData(position, mList);
        }
        return convertView;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}
