package com.cqyanyu.backing.ui.holder.statistics;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.statistics.InspectionTaskActivity;
import com.cqyanyu.backing.ui.entity.statistics.InspectionTaskEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 巡检任务
 * Created by Administrator on 2017/7/12.
 */

public class InspectionTaskHolder extends IViewHolder {
    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_inspection_task;
    }

    private class ViewHolder extends XViewHolder<InspectionTaskEntity> {
        XTextView tvDeviceCount;
        XTextView tvInspectRate;
        XTextView tvTaskNum;//任务数
        XTextView tvStatus;//状态
        XTextView tvDate;
        XTextView tvInspectUser;
        TextView tvNum;

        ViewHolder(View itemView, RecyclerView.Adapter adapter) {
            super(itemView, adapter);
        }

        /**
         * 初始化
         *
         * @param rootView
         */
        @Override
        protected void initView(View rootView) {
            tvDate = (XTextView) rootView.findViewById(R.id.tv_date);//时间
            tvTaskNum = (XTextView) rootView.findViewById(R.id.tv_task_num);//设备总数
            tvDeviceCount = (XTextView) rootView.findViewById(R.id.tv_devicecount);//巡检设备
            tvInspectRate = (XTextView) rootView.findViewById(R.id.tv_inspect_rate);//巡检率
            tvStatus = (XTextView) rootView.findViewById(R.id.tv_status);//状态
            tvInspectUser = (XTextView) rootView.findViewById(R.id.tv_inspect_user);
            tvNum = (TextView) rootView.findViewById(R.id.tv_num);
        }

        /**
         * 绑定数据
         *
         * @param itemData
         */
        @Override
        protected void onBindData(InspectionTaskEntity itemData) {
            tvNum.setText(getAdapterPosition() + 1 + "");//设置序列号
            if (InspectionTaskActivity.LABEL_TYPE == 1) {
                tvDate.setXText(XDateUtil.getStringByFormat(itemData.getDate() * 1000, "yyyy年MM月dd日"));
            } else {
                tvDate.setXText(XDateUtil.getStringByFormat(itemData.getDate() * 1000, "yyyy年MM月"));
            }
            /**设置位置*/
            if (!TextUtils.isEmpty(itemData.getBuildstr()) && CommonInfo.getInstance().getUserInfo().isnode()) {
                tvTaskNum.setXText(itemData.getBuildstr());
            } else {
                tvTaskNum.setXText(itemData.getUnitstr() + "@" + itemData.getBuildstr());
            }
            tvDeviceCount.setXText(itemData.getDevicecount() + "");
            /**设置巡检率*/
            float inspectRate = 100f * itemData.getInspectioncount() / itemData.getDevicecount();
            tvInspectRate.setXText(NumberUtils.setDecimalFloat(inspectRate) + "%");
            tvInspectUser.setXText(itemData.getInspectionmanstr());
            /**设置状态*/
            if (inspectRate < 85) {
                tvStatus.setXText("不合格");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_red));
            } else if (inspectRate >= 85 && inspectRate < 95) {
                tvStatus.setXText("预警");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_orange));
            } else {
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_green));
                tvStatus.setXText("合格");
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}
