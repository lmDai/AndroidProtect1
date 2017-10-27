package com.cqyanyu.backing.ui.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用ViewHolder
 * Created by zj on 2017/4/19.
 */
public abstract class XViewHolder<T> implements View.OnClickListener {
    protected Context mContext;
    protected int position;
    protected List<T> mList;
    protected XBaseAdapter adapter;
    protected OnClickListener listener;
    protected T itemEntity;
    private View rootView;

    private XViewHolder(ViewGroup viewGroup) {
        this.mContext = viewGroup.getContext();
    }

    protected XViewHolder(ViewGroup viewGroup, int layoutRes) {
        this(viewGroup);
        rootView = LayoutInflater.from(mContext).inflate(layoutRes, viewGroup, false);
        rootView.setOnClickListener(this);
    }

    public void onBindView(XBaseAdapter adapter, View rootView) {
        this.adapter = adapter;
        onBindView(rootView);
    }

    public void onBindData(int position, List<T> mList) {
        this.position = position;
        this.mList = mList;
        this.itemEntity = mList.get(position);
        onBindData(itemEntity);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    protected abstract void onBindData(T itemEntity);

    public View getRootView() {
        return rootView;
    }

    protected abstract void onBindView(View rootView);
}
