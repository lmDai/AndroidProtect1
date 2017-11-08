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
        protected View rootView;
        protected XTextView tvDate;
        protected XTextView tvAlarmcount;
        protected LinearLayout llAlarm;
        protected XTextView tvAlarmunhandle;
        protected LinearLayout llNoAlarm;
        protected XTextView tvProblemcount;
        protected LinearLayout llProblem;
        protected XTextView tvProblemunhandle;
        protected LinearLayout llNoProblem;
        protected XTextView tvOtherCount;
        protected LinearLayout llOther;
        protected XTextView tvOtherUnhandle;
        protected LinearLayout llNoOther;
        protected XTextView tvWarncount;
        protected LinearLayout llWarn;
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
            tvDate = (XTextView) rootView.findViewById(R.id.tv_date);
            tvAlarmcount = (XTextView) rootView.findViewById(R.id.tv_alarmcount);
            llAlarm = (LinearLayout) rootView.findViewById(R.id.ll_alarm);
            llAlarm.setOnClickListener(this);
            tvAlarmunhandle = (XTextView) rootView.findViewById(R.id.tv_alarmunhandle);
            llNoAlarm = (LinearLayout) rootView.findViewById(R.id.ll_no_alarm);
            llNoAlarm.setOnClickListener(this);
            tvProblemcount = (XTextView) rootView.findViewById(R.id.tv_problemcount);
            llProblem = (LinearLayout) rootView.findViewById(R.id.ll_problem);
            llProblem.setOnClickListener(this);
            tvProblemunhandle = (XTextView) rootView.findViewById(R.id.tv_problemunhandle);
            llNoProblem = (LinearLayout) rootView.findViewById(R.id.ll_no_problem);
            llNoProblem.setOnClickListener(this);
            tvOtherCount = (XTextView) rootView.findViewById(R.id.tv_other_count);
            llOther = (LinearLayout) rootView.findViewById(R.id.ll_other);
            llOther.setOnClickListener(this);
            tvOtherUnhandle = (XTextView) rootView.findViewById(R.id.tv_other_unhandle);
            llNoOther = (LinearLayout) rootView.findViewById(R.id.ll_no_other);
            llNoOther.setOnClickListener(this);
            tvWarncount = (XTextView) rootView.findViewById(R.id.tv_warncount);
            llWarn = (LinearLayout) rootView.findViewById(R.id.ll_warn);
            llWarn.setOnClickListener(this);
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
            tvAlarmcount.setXText(String.valueOf(itemData.getAlarmcount()));
            tvProblemcount.setXText(String.valueOf(itemData.getProblemcount()));
            tvWarncount.setXText(String.valueOf(itemData.getWarncount()));
            tvOtherCount.setXText(String.valueOf(itemData.getOthercount()));
            //判断未处理数是否小于0，是就设置为0
            int alarmUnHandle = itemData.getAlarmcount() - itemData.getAlarmhandle();
            int problemUnHandle = itemData.getProblemcount() - itemData.getProblemhandle();
            int warnUnHandle = itemData.getWarncount() - itemData.getWarnhandle();
            int otherUnHandle = itemData.getOthercount() - itemData.getOtherhandle();
            tvAlarmunhandle.setXText(alarmUnHandle < 0 ? "0" : alarmUnHandle + "");
            tvProblemunhandle.setXText(problemUnHandle < 0 ? "0" : problemUnHandle + "");
            tvOtherUnhandle.setXText(otherUnHandle < 0 ? "0" : otherUnHandle + "");

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ll_alarm) {
                mContext.startActivity(new Intent(mContext, CountFireActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra("searchType", activity.getType())
                        .putExtra("label", "火警")
                        .putExtra(CountFireActivity.HANDLE, "handle")
                        .putExtra("type", 1));
            } else if (v.getId() == R.id.ll_no_alarm) {
                mContext.startActivity(new Intent(mContext, CountFireActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra("searchType", activity.getType())
                        .putExtra("label", "火警")
                        .putExtra(CountFireActivity.HANDLE, "unhandle")
                        .putExtra("type", 1));
            } else if (v.getId() == R.id.ll_problem) {
                mContext.startActivity(new Intent(mContext, CountFaultActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra(CountFaultActivity.HANDLE, "handle")
                        .putExtra("searchType", activity.getType()));
            } else if (v.getId() == R.id.ll_no_problem) {
                mContext.startActivity(new Intent(mContext, CountFaultActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra(CountFaultActivity.HANDLE, "unhandle")
                        .putExtra("searchType", activity.getType()));
            } else if (v.getId() == R.id.ll_other) {
                mContext.startActivity(new Intent(mContext, CountFireActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra("searchType", activity.getType())
                        .putExtra("label", "其他")
                        .putExtra(CountFireActivity.HANDLE, "handle")
                        .putExtra("type", 4));
            } else if (v.getId() == R.id.ll_no_other) {
                mContext.startActivity(new Intent(mContext, CountFireActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra("searchType", activity.getType())
                        .putExtra("label", "其他")
                        .putExtra(CountFireActivity.HANDLE, "unhandle")
                        .putExtra("type", 4));
            } else if (v.getId() == R.id.ll_warn) {
                mContext.startActivity(new Intent(mContext, CountWarnActivity.class)
                        .putExtra("date", itemData.getDate())
                        .putExtra("searchType", activity.getType()));
            }
        }
    }
}
