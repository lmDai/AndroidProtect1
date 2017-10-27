package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.EntryActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 词条
 * Created by Administrator on 2017/7/19.
 */
public class EntryHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_entry;
    }

    private class ViewHolder extends XViewHolder<EntryEntity> {
        TextView tvEntry;
        ImageView ivIcon;
        EntryActivity activity;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            tvEntry = (TextView) rootView.findViewById(R.id.tv_entry);
            ivIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
            tvEntry.setOnClickListener(this);
            ivIcon.setOnClickListener(this);
            if (mContext instanceof EntryActivity) {
                activity = (EntryActivity) mContext;
            }
        }

        @Override
        protected void onBindData(EntryEntity itemData) {
            tvEntry.setText(itemData.getName());
        }

        @Override
        public void onClick(View v) {
            if (activity != null) {
                switch (v.getId()) {
                    case R.id.tv_entry:
                        activity.gotoNext(itemData, false);
                        break;

                    case R.id.iv_icon:
                        activity.gotoNext(itemData, !TextUtils.equals(itemData.getChildcount(), "0"));
                        break;
                }
            }
        }
    }
}
