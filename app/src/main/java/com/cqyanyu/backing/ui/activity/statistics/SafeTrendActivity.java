package com.cqyanyu.backing.ui.activity.statistics;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.statistics.SafeTrendView;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

/**
 * 安全趋势
 * Created by Administrator on 2017/7/7.
 */

public class SafeTrendActivity extends BaseActivity<BasePresenter> implements SafeTrendView {

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_safe_trend;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        setTopTitle("舆情分析");
    }

}
