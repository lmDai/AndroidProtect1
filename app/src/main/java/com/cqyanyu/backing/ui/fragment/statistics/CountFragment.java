package com.cqyanyu.backing.ui.fragment.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.adapter.base.XBaseAdapter;
import com.cqyanyu.backing.ui.entity.statistics.CountBean;
import com.cqyanyu.backing.ui.holder.statistics.CountItemHolder;
import com.cqyanyu.backing.ui.mvpview.statistics.CountView;
import com.cqyanyu.backing.ui.presenter.statistics.CountPresenter;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.MPChartHelper;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计首页
 * Created by Administrator on 2017/8/1.
 */

public class CountFragment extends BaseFragment<CountPresenter> implements CountView {
    private GridView gridView;
    private XBaseAdapter adapter;
    private BarChart barChart1;
    private List<String> xAxisValues;
    private boolean isFirstVisible = true;
    private XTextView tvWarnCount;
    private boolean isVisiBleToUser;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        gridView = (GridView) view.findViewById(R.id.grid_view);
        barChart1 = (BarChart) view.findViewById(R.id.barChart1);
        tvWarnCount = (XTextView) view.findViewById(R.id.tv_WarnCount);
        List<Float> alarmTotals = new ArrayList<Float>();
        alarmTotals.add((float) 0);
        alarmTotals.add((float) 0);
        alarmTotals.add((float) 0);
        alarmTotals.add((float) 0);
        List<Float> handleTotals = new ArrayList<Float>();
        handleTotals.add((float) 0);
        handleTotals.add((float) 0);
        handleTotals.add((float) 0);
        handleTotals.add((float) 0);
        xAxisValues = new ArrayList<>();
        xAxisValues.add("火警");
        xAxisValues.add("预警");
        xAxisValues.add("故障");
        xAxisValues.add("其他");
        MPChartHelper.setTwoBarChart(barChart1, xAxisValues, alarmTotals, handleTotals, "总数", "已处理");
    }

    @Override
    protected CountPresenter createPresenter() {
        return new CountPresenter();
    }

    @Override
    protected int getLayout() {
        showBack = false;
        return R.layout.fragment_count;
    }

    @Override
    public void onResume() {
        if (isVisiBleToUser && !isFirstVisible && mPresenter != null) {
            mPresenter.getCount();
            mPresenter.getMessageAlar();
        }
        super.onResume();
    }

    @Override
    protected void initListener() {
        adapter = new XBaseAdapter(CountItemHolder.class);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        setTopTitle("统计");
    }

    @Override
    public void setCountItem(List<CountBean> hList) {
        if (adapter != null) adapter.setList(hList);
    }

    @Override
    public void setAlarmTotal(List<Float> alarmTotals, List<Float> handleTotals) {
        MPChartHelper.setTwoBarChart(barChart1, xAxisValues, alarmTotals, handleTotals, "总数", "已处理");
    }

    @Override
    public void updateData() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public void setWarnCount(int warnCount) {
        tvWarnCount.setXText(warnCount + "");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisiBleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFirstVisible) {
                if (mPresenter != null) mPresenter.init();
                isFirstVisible = false;
            } else {
                mPresenter.getMessageAlar();
                mPresenter.getCount();
            }
        } else {
            this.isVisiBleToUser = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
