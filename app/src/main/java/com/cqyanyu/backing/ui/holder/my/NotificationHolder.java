package com.cqyanyu.backing.ui.holder.my;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cqyanyu.backing.ui.entity.my.NotificationInfoEntity;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 通知消息
 * Created by Administrator on 2017/7/17.
 */

public class NotificationHolder extends IViewHolder {
    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    private class ViewHolder extends XViewHolder<NotificationInfoEntity> {

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {

        }

        @Override
        protected void onBindData(NotificationInfoEntity itemData) {

        }

        @Override
        public void onClick(View v) {
        }
    }
}
