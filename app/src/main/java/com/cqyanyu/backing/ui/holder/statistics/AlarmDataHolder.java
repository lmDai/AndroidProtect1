package com.cqyanyu.backing.ui.holder.statistics;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.statistics.AlarmActivity;
import com.cqyanyu.backing.ui.activity.statistics.CountFaultActivity;
import com.cqyanyu.backing.ui.activity.statistics.CountFireActivity;
import com.cqyanyu.backing.ui.activity.statistics.CountWarnActivity;
import com.cqyanyu.backing.ui.entity.statistics.AlarmDataEntity;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.view.recyclerView.IViewHolder;
import com.cqyanyu.mvpframework.view.recyclerView.XViewHolder;

/**
 * 告警数据
 * Created by Administrator on 2017/7/12.
 */

public class AlarmDataHolder extends IViewHolder {

    @Override
    protected XViewHolder create(View view, RecyclerView.Adapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.item_alarm_data;
    }

    private class ViewHolder extends XViewHolder<AlarmDataEntity> {
        XTextView tvDate;
        XTextView tvAlarmCount;
        XTextView tvProblemCount;
        XTextView tvWarnCount;
        XTextView tvAlarmUnHandle;
        XTextView tvProblemUnHandle;
        XTextView tvOtherCount;
        XTextView tvOtherUnHandle;
        LinearLayout llAlarm;
        LinearLayout llProblem;
        LinearLayout llWarn;
        LinearLayout llOther;
        AlarmActivity activity;

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
            tvDate = (XTextView) rootView.findViewById(R.id.tv_date);//筛选时间
            tvAlarmCount = (XTextView) rootView.findViewById(R.id.tv_alarmcount);//告警总数
            tvProblemCount = (XTextView) rootView.findViewById(R.id.tv_problemcount);//故障总数
            tvWarnCount = (XTextView) rootView.findViewById(R.id.tv_warncount);//预警总数
            tvAlarmUnHandle = (XTextView) rootView.findViewById(R.id.tv_alarmunhandle);//告警未处理
            tvProblemUnHandle = (XTextView) rootView.findViewById(R.id.tv_problemunhandle);//故障未处理
            tvOtherCount = (XTextView) rootView.findViewById(R.id.tv_other_count);
            tvOtherUnHandle = (XTextView) rootView.findViewById(R.id.tv_other_unhandle);
            llAlarm = (LinearLayout) rootView.findViewById(R.id.ll_alarm);
            llProblem = (LinearLayout) rootView.findViewById(R.id.ll_problem);
            llWarn = (LinearLayout) rootView.findViewById(R.id.ll_warn);
            llOther = (LinearLayout) rootView.findViewById(R.id.ll_other);
            llAlarm.setOnClickListener(this);
            llProblem.setOnClickListener(this);
            llWarn.setOnClickListener(this);
            llOther.setOnClickListener(this);
            activity = (AlarmActivity) mContext;
        }

        /**
         * 绑定数据
         *
         * @param itemData
         */
        @Override
        protected void onBindData(AlarmDataEntity itemData) {
            if (AlarmActivity.LABEL_TYPE == 1) {
                tvDate.setXText(XDateUtil.getStringByFormat(itemData.getDate() * 1000, "yyyy年MM月dd日"));
            } else {
                tvDate.setXText(XDateUtil.getStringByFormat(itemData.getDate() * 1000, "yyyy年MM月"));
            }
            tvAlarmCount.setXText(String.valueOf(itemData.getAlarmcount()));
            tvProblemCount.setXText(String.valueOf(itemData.getProblemcount()));
            tvWarnCount.setXText(String.valueOf(itemData.getWarncount()));
            tvOtherCount.setXText(String.valueOf(itemData.getOthercount()));
            //判断未处理数是否小于0，是就设置为0
            int alarmUnHandle = itemData.getAlarmcount() - itemData.getAlarmhandle();
            int problemUnHandle = itemData.getProblemcount() - itemData.getProblemhandle();
            int warnUnHandle = itemData.getWarncount() - itemData.getWarnhandle();
            int otherUnHandle = itemData.getOthercount() - itemData.getOtherhandle();
            tvAlarmUnHandle.setXText(alarmUnHandle < 0 ? "0" : alarmUnHandle + "");
            tvProblemUnHandle.setXText(problemUnHandle < 0 ? "0" : problemUnHandle + "");
            tvOtherUnHandle.setXText(otherUnHandle < 0 ? "0" : otherUnHandle + "");

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_alarm:
                    mContext.startActivity(new Intent(mContext, CountFireActivity.class)
                            .putExtra("date", itemData.getDate())
                            .putExtra("searchType", activity.getType())
                            .putExtra("label", "火警")
                            .putExtra("type", 1));
                    break;
                case R.id.ll_problem:
                    mContext.startActivity(new Intent(mContext, CountFaultActivity.class)
                            .putExtra("date", itemData.getDate())
                            .putExtra("searchType", activity.getType()));
                    break;
                case R.id.ll_other:
                    mContext.startActivity(new Intent(mContext, CountFireActivity.class)
                            .putExtra("date", itemData.getDate())
                            .putExtra("searchType", activity.getType())
                            .putExtra("label", "其他")
                            .putExtra("type", 4));
                    break;
                case R.id.ll_warn:
                    mContext.startActivity(new Intent(mContext, CountWarnActivity.class)
                            .putExtra("date", itemData.getDate())
                            .putExtra("searchType", activity.getType()));
                    break;
            }
        }
    }
}
