package com.cqyanyu.backing.ui.holder.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.home.MyTaskActivity;
import com.cqyanyu.backing.ui.activity.home.TaskDetailsActivity;
import com.cqyanyu.backing.ui.entity.home.MyTaskEntity;
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
public class MyTaskHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_my_task;
    }

    private class ViewHolder extends XViewHolder<MyTaskEntity> {

        ItemOptionView iovTaskNum;
        ItemOptionView iovDeviceNum;
        ItemOptionView iovCheckRate;
        TextView iovState;
        ImageView ivState;
        MyTaskActivity activity;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        @Override
        protected void initView(View rootView) {
            iovTaskNum = (ItemOptionView) rootView.findViewById(R.id.iov_task_num);
            iovDeviceNum = (ItemOptionView) rootView.findViewById(R.id.iov_device_num);
            iovCheckRate = (ItemOptionView) rootView.findViewById(R.id.iov_check_rate);
            iovState = (TextView) rootView.findViewById(R.id.iov_state);
            ivState = (ImageView) rootView.findViewById(R.id.iv_state);
            if (mContext instanceof MyTaskActivity) {
                activity = (MyTaskActivity) mContext;
            }
            iovTaskNum.setOnClickListener(this);
            itemView.setClickable(false);
            iovState.setOnClickListener(this);
        }

        @Override
        protected void onBindData(MyTaskEntity itemData) {
            float rate = getInspectionRate(itemData);
            String stat = rate > 95 ? "合格" : (rate < 85 ? "不合格" : "预警");
            //任务数
            iovTaskNum.setContent(itemData.getBuildstr());
            //设备数
            iovDeviceNum.setContent("" + itemData.getDevicecount());
            //巡检率
            iovCheckRate.setContent(NumberUtils.setDecimalFloat(rate) + "%");
            // 状态
            iovState.setText(stat);
            // 设置箭头
            ivState.setVisibility(itemData.getIsenable() == 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iov_state:
                    if (itemData.getIsenable() == 0 && activity != null) {
                        activity.gotoScan(itemData.getOid(), getInspectionRate(itemData));
                    }
                    break;
                case R.id.iov_task_num:
                    mContext.startActivity(new Intent(mContext, TaskDetailsActivity.class)
                            .putExtra(TaskDetailsActivity.KEY_TASK_ID, itemData.getOid())
                    );
                    break;
            }
        }

        //获取巡检率
        private float getInspectionRate(MyTaskEntity itemData) {
            return Float.parseFloat(NumberUtils.setDecimalFloat(itemData.getInspectioncount() * 100.0f / itemData.getDevicecount()));
        }

        private List getList() {
            if (adapter instanceof XRecyclerViewAdapter) {
                XRecyclerViewAdapter xAdapter = (XRecyclerViewAdapter) this.adapter;
                return xAdapter.getData(0);
            }
            return new ArrayList();
        }
    }
}

