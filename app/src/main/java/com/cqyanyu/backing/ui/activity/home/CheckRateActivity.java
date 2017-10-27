package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.CheckRateView;
import com.cqyanyu.backing.ui.presenter.home.CheckRatePresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 巡检率
 * Created by Administrator on 2017/7/10.
 */
public class CheckRateActivity extends BaseActivity<CheckRatePresenter> implements CheckRateView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected CheckRatePresenter createPresenter() {
        return new CheckRatePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_check_rate;
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
}
