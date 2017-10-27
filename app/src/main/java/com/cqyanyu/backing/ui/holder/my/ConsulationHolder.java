package com.cqyanyu.backing.ui.holder.my;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.my.ConsulationDetailsActivity;
import com.cqyanyu.backing.ui.entity.my.ConsulationEntity;
import com.cqyanyu.backing.ui.widget.app.XImageView;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 消防资讯
 * Created by Administrator on 2017/7/17.
 */

public class ConsulationHolder extends IViewHolder {
    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_consulation;
    }

    private class ViewHolder extends XViewHolder<ConsulationEntity> {
        XImageView imgHead;
        XTextView tvTitle;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            imgHead = (XImageView) rootView.findViewById(R.id.img_head);
            tvTitle = (XTextView) rootView.findViewById(R.id.tv_title);
        }

        @Override
        protected void onBindData(ConsulationEntity itemData) {
            Glide.with(mContext)
                    .load(itemData.getImg_id())
                    .into(imgHead);
            tvTitle.setXText(itemData.getTitle());
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, ConsulationDetailsActivity.class));
        }
    }
}
