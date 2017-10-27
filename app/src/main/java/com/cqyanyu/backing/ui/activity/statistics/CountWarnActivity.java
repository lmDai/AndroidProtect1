package com.cqyanyu.backing.ui.activity.statistics;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.statistics.CountAlarmView;
import com.cqyanyu.backing.ui.presenter.statistics.CountAlarmListPresenter;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import java.util.Date;
import java.util.Map;

public class CountWarnActivity extends BaseActivity<CountAlarmListPresenter> implements CountAlarmView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected CountAlarmListPresenter createPresenter() {
        return new CountAlarmListPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_count_fault;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public String getStartDate() {
        return getIntent().getLongExtra("date", (long) 0) + "";
    }

    @Override
    public String getEndDate() {
        Map<String, String> hashMap = null;
        Date date = new Date();
        date.setTime(getIntent().getLongExtra("date", (long) 0) * 1000);
        if (getIntent().getIntExtra("searchType", 0) == 2) {
            hashMap = MyDate.getFirstday_Lastday_Month(date);
            return MyDate.stringToLong(hashMap.get("last"), "yyyy-MM-dd HH:mm:ss") / 1000 + "";
        }
        return getIntent().getLongExtra("date", (long) 0) + 86399 + "";
    }
}
