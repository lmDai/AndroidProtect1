package com.cqyanyu.backing.ui.holder.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.KnowledgeDetailActivity;
import com.cqyanyu.backing.ui.entity.home.FireKnowledgeEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 消防知识
 * Created by Administrator on 2017/7/18.
 */

public class FireKnowledgeHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_fire_knowledge;
    }

    private class ViewHolder extends XViewHolder<FireKnowledgeEntity> {
        XTextView tvTitle;
        XTextView tvTime;
        XTextView tvLocation;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvTitle = (XTextView) rootView.findViewById(R.id.tv_title);
            tvTime = (XTextView) rootView.findViewById(R.id.tv_time);
            tvLocation = (XTextView) rootView.findViewById(R.id.tv_location);
        }

        @Override
        protected void onBindData(FireKnowledgeEntity itemData) {
            tvTitle.setXText(itemData.getFilename());
            tvTime.setXText(XDateUtil.getStringByFormat(itemData.getDate() * 1000, "yyyy-MM-dd HH:mm:ss"));
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, KnowledgeDetailActivity.class).putExtra("fireknowledge", itemData));
        }
    }
}