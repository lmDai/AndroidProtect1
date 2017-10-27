package com.cqyanyu.backing.ui.holder.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.CheckRateEntity;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.view.ItemOptionView;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerViewAdapter;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的任务
 * Created by Administrator on 2017/7/18.
 */
public class CheckRateHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_all_task;
    }

    private class ViewHolder extends XViewHolder<CheckRateEntity> {

        TextView iovTaskNum;
        ItemOptionView iovDeviceNum;
        ItemOptionView iovCheckRate;
        TextView iovState;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            iovTaskNum = (TextView) rootView.findViewById(R.id.iov_task_num);
            iovDeviceNum = (ItemOptionView) rootView.findViewById(R.id.iov_device_num);
            iovCheckRate = (ItemOptionView) rootView.findViewById(R.id.iov_check_rate);
            iovState = (TextView) rootView.findViewById(R.id.iov_state);
            tvNum = (TextView) rootView.findViewById(R.id.tv_regulatory_num);
            itemView.setClickable(false);
        }

        @Override
        protected void onBindData(CheckRateEntity itemData) {
            tvNum.setText(getAdapterPosition() + 1 + "");
            float rate = getInspectionRate(itemData);
            String stat = rate > 95 ? "合格" : (rate < 85 ? "不合格" : "预警");
            //iovTaskNum.setContent(itemData.getBuildstr());
            /**设置位置*/
            if (!TextUtils.isEmpty(itemData.getBuildstr()) && CommonInfo.getInstance().getUserInfo().isnode()) {
                iovTaskNum.setText(itemData.getBuildstr());
            } else {
                iovTaskNum.setText(itemData.getUnitstr() + "@" + itemData.getBuildstr());
            }
            //任务数
            iovDeviceNum.setContent("" + itemData.getDevicecount());
            //巡检率
            iovCheckRate.setContent(NumberUtils.setDecimalFloat(rate) + "%");
            if (rate < 85) {
                iovState.setText("不合格");
                iovState.setTextColor(mContext.getResources().getColor(R.color.color_red));
            } else if (rate >= 85 && rate < 95) {
                iovState.setText("预警");
                iovState.setTextColor(mContext.getResources().getColor(R.color.color_orange));
            } else {
                iovState.setTextColor(mContext.getResources().getColor(R.color.color_green));
                iovState.setText("合格");
            }
        }

        //获取巡检率
        private float getInspectionRate(CheckRateEntity itemData) {
            return itemData.getInspectioncount() * 100.0f / itemData.getDevicecount();
        }

        private List getList() {
            if (adapter instanceof XRecyclerViewAdapter) {
                XRecyclerViewAdapter xAdapter = (XRecyclerViewAdapter) this.adapter;
                return xAdapter.getData(0);
            }
            return new ArrayList();
        }

        @Override
        public void onClick(View v) {

        }
    }
}

