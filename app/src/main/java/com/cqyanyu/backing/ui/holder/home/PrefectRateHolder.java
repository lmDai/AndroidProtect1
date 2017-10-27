package com.cqyanyu.backing.ui.holder.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.PerfectSecondActivity;
import com.cqyanyu.backing.ui.entity.home.PerfectRateEntity;
import com.cqyanyu.mvpframework.utils.XScreenUtils;
import com.cqyanyu.mvpframework.view.ItemOptionView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 设备总数
 * Created by Administrator on 2017/7/18.
 */
public class PrefectRateHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_perfect_rate;
    }

    private class ViewHolder extends XViewHolder<PerfectRateEntity> {

        ItemOptionView iovContent;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            iovContent = (ItemOptionView) rootView.findViewById(R.id.iov_content);
            iovContent.findViewById(R.id.content).setPadding(0, 0, XScreenUtils.dip2px(mContext, 16), 0);
        }

        @Override
        protected void onBindData(PerfectRateEntity itemData) {
            //设置名字
            iovContent.setTitle(itemData.getTypestr());
            //设置完好率
            iovContent.setContent(itemData.getTotalcount() + "");
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, PerfectSecondActivity.class)
                    .putExtra("typeStr", itemData.getTypestr())
                    .putExtra("mTypeId", itemData.getProblemid()));
        }
    }
}
